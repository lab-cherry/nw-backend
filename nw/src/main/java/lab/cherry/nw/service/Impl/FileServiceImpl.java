package lab.cherry.nw.service.Impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.FileEntity;
import lab.cherry.nw.repository.FileRepository;
import lab.cherry.nw.service.FileService;
import lab.cherry.nw.service.MinioService;
import lab.cherry.nw.util.FormatConverter;
import lab.cherry.nw.util.HttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

	private final MinioService minioService;
  private final FileRepository fileRepository;

	@Value("${minio.url}")
	private String minioUrl;

	@Value("${minio.access-key}")
	private String accessKey;

	@Value("${minio.secret-key}")
	private String secretKey;

	public Page<FileEntity> getFiles(Pageable pageable) {
		return fileRepository.findAll(pageable);
	}
	
	@Override
    public List<String> uploadFiles(Map<String, String> info, List<MultipartFile> files) {

		String orgId = info.get("org");	// 조직명
		String userId = info.get("user"); 	// 사용자명
		String type = info.get("type");
		String qsheetSeq= info.get("qsheetSeq");		 	// 파일 구분명
		String bucketName = null;

		String destPath;
		if (userId != null) {
			// user가 입력되면, /user/{userId}으로 Path 지정
			bucketName = "user";
			destPath = userId + "/" + qsheetSeq +"/";
		} else {
			// user값이 없을 시, {org_objectId}/관리/로 Path 지정
			bucketName = orgId;
			destPath = "/관리/";
		}

		List<String> fileObjectIds = new ArrayList<>();

		for (MultipartFile file : files) {
			String originalFilename = (userId != null) ? file.getOriginalFilename() : type + "/" + file.getOriginalFilename();
			String filepath = destPath + originalFilename;

			log.error("objectName is {}", filepath);

			try {
				minioService.uploadObject(bucketName, filepath, file);
            } catch (IOException e) {
				log.error(e.getMessage());
			} catch (NoSuchAlgorithmException | InvalidKeyException e) {
				throw new RuntimeException(e);
			}

			String fileUrl = "/api/v1/file/download/" + bucketName + "?path=" + filepath;
            // 파일 객체 ID 저장
			fileObjectIds.add(fileUrl);

			FileEntity fileEntity = FileEntity.builder()
						.name(file.getOriginalFilename())
						.size(FormatConverter.convertInputBytes(file.getSize()))
						.type(file.getContentType())
						.path(filepath)
						.url(fileUrl)
						.userid(userId)
						.orgid(bucketName)
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

	@Override
	public byte[] downloadZip(String bucketName, String objectName) {

		objectName = Base64.encodeBase64String(objectName.getBytes());
		String addr = minioUrl.substring(0, minioUrl.length()-5) + ":9001";

		try {
				URI uri = UriComponentsBuilder
						.fromUriString(addr)
						.path("/api/v1/buckets/{bucketName}/objects/download")
						// .encode()
						.queryParam("prefix", objectName)
						.build()
						.expand(bucketName, objectName)
						.toUri();
			
			log.error("uri {}", uri);
			return HttpUtils.getForObject(uri);

		} catch (Exception ex) {
			log.error("error {}", ex);
			log.error("error.msg {}", ex.getMessage());
			// throw new CustomException(ErrorCode.URL_NOTFOUND);
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

	@Transactional(readOnly = true)
    public Page<FileEntity> findPageByOrgId(String orgid, Pageable pageable) {
		return fileRepository.findPageByOrgid(orgid, pageable);
	}


	@Transactional(readOnly = true)
    public FileEntity findById(String id) {
		return fileRepository.findByid(id).orElseThrow(() -> new EntityNotFoundException("file with Id " + id + " Not Found."));
	}

	@Transactional(readOnly = true)
    public FileEntity findByPath(String path) {
		return fileRepository.findByPath(path).orElseThrow(() -> new EntityNotFoundException("file with Path " + path + " Not Found."));
	}

	public void deleteFiles(String orgId, List<String> images) {

		for (String image : images) {
			FileEntity fileEntity = findByPath(image); // checkFileExists

			try {
				minioService.deleteObject(orgId, image);

				System.out.println("파일 삭제 성공: " + image);
			} catch (IOException e) {
				log.error("파일 콘텐츠 확인 중 오류 발생: {}", e.getMessage());
			} catch (NoSuchAlgorithmException | InvalidKeyException e) {
				throw new RuntimeException(e);
			}

			fileRepository.delete(fileRepository.findByPath(image).orElseThrow(() -> new EntityNotFoundException("File with Path " + image + " Not Found.")));
		}
	}
}