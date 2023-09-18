package lab.cherry.nw.service.Impl;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.FileEntity;
import lab.cherry.nw.repository.FileRepository;
import lab.cherry.nw.service.FileService;
import lab.cherry.nw.service.MinioService;
import lab.cherry.nw.service.OrgService;
import lab.cherry.nw.util.Common;
import lab.cherry.nw.util.FormatConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

	private final MinioService minioService;
    private final FileRepository fileRepository;

	public Page<FileEntity> getFiles(Pageable pageable) {
		return fileRepository.findAll(pageable);
	}
	
	@Override
    public List<String> uploadFiles(Map<String, String> info, List<MultipartFile> files) {

		String orgId = info.get("org");	// 조직명
		String userName = info.get("user"); 	// 사용자명
		String type = info.get("type");		 	// 파일 구분명

		String destPath;
		if (userName != null) {
			// user가 입력되면, {org_objectId}/고객/{userName}으로 Path 지정
			destPath = "/고객/" + userName + "/";
		} else {
			// user값이 없을 시, {org_objectId}/관리/로 Path 지정
			destPath = "/관리/";
		}

		List<String> fileObjectIds = new ArrayList<>();

		for (MultipartFile file : files) {
			String originalFilename = (userName != null) ? file.getOriginalFilename() : type + "/" + file.getOriginalFilename();
			String filePath = destPath + originalFilename;

			log.error("filePath is {}", filePath);

			try {
				minioService.uploadObject(orgId, filePath, file);
            } catch (IOException e) {
				log.error(e.getMessage());
			} catch (NoSuchAlgorithmException | InvalidKeyException e) {
				throw new RuntimeException(e);
			}

            // 파일 객체 ID 저장
			fileObjectIds.add(filePath);

			FileEntity fileEntity = FileEntity.builder()
						.name(file.getOriginalFilename())
						.size(FormatConverter.convertInputBytes(file.getSize()))
						.type(file.getContentType())
						.ext(Common.getFileExtension(file))
						.path(filePath)
						.userid(userName)
						.orgid(orgId)
						.created_at(Instant.now())
						.build();

			fileRepository.save(fileEntity);
		}
		return fileObjectIds;
	}

	public List<String> getAllFiles(String orgId, String path) {

		try {
			return minioService.listObjects(orgId, path);

		} catch (IOException e) {
			log.error(e.getMessage());
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
    public InputStream downloadFile(String orgId, String path) {

		try {
			return minioService.getObject(orgId, path);

		} catch (IOException e) {
			log.error("파일 콘텐츠 확인 중 오류 발생: {}", e.getMessage());
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

     public void deleteById(String id) {
		FileEntity fileEntity = findById(id);

		try {
			minioService.deleteObject(fileEntity.getId(), fileEntity.getPath());

			System.out.println("파일 삭제 성공: " + fileEntity.getName());
		} catch (IOException e) {
			log.error("파일 콘텐츠 확인 중 오류 발생: {}", e.getMessage());
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}

		fileRepository.delete(fileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("File with Id " + id + " Not Found.")));
	}


	@Transactional(readOnly = true)
    public Page<FileEntity> findPageByName(String name, Pageable pageable) {
		return fileRepository.findPageByName(name, pageable);
	}

//	@Transactional(readOnly = true)
//    public Page<FileEntity> findPageByUserId(String userid, Pageable pageable) {
//		return fileRepository.findPageByUserid(userid, pageable);
//	}

	@Transactional(readOnly = true)
    public Page<FileEntity> findPageByOrgId(String orgid, Pageable pageable) {
		return fileRepository.findPageByOrgid(orgid, pageable);
	}


	@Transactional(readOnly = true)
    public FileEntity findById(String id) {
		return fileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("file with Id " + id + " Not Found."));
	}
}