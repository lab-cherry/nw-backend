package lab.cherry.nw.service.Impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.FileEntity;
import lab.cherry.nw.repository.FileRepository;
import lab.cherry.nw.service.FileService;
import lab.cherry.nw.util.FormatConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

	@Value("${lab.cherry.nw.uploadPath}")
	private String uploadPath;
  private final FileRepository fileRepository;	
	private final GridFsTemplate template;
	private final GridFsOperations operations;

	public Page<FileEntity> getFiles(Pageable pageable) {
		return fileRepository.findAll(pageable);
	}
	
	@Override
    public List<String> uploadFiles(Map<String, String> info, List<MultipartFile> files) {
		
		String orgName = (info.get("org") != null) ? info.get("org") : "";				// 조직명
		String userName = info.get("username");	// 사용자명
		String type = info.get("type");					// 타입 (ex. 웨딩홀, 연회장, 폐백실)
		String kind = info.get("kind");					// 구분 (ex. 웨딩홀명, 연회장명, 폐백실명)
		String seq = info.get("seq");						// 고유번호

		Path subPath = null;
		if(userName == null) {
			subPath = Paths.get("조직" + File.separator + orgName + File.separator + type + File.separator + kind);
		} else {
			subPath = Paths.get("사용자" + File.separator + userName + File.separator + kind + "-" + seq);
		}

		String directoryPath = folderChk("mkdir", subPath.toString());

		List<String> fileUrls = new ArrayList<>();

		for (MultipartFile file : files) {
			

			DBObject metadata = new BasicDBObject();
			metadata.put("seq", seq);
			metadata.put("name", file.getOriginalFilename());
			metadata.put("size", file.getSize());
			metadata.put("type", file.getContentType());
			metadata.put("kind", kind);
			metadata.put("path", directoryPath);

			Object fileObjectID = null;
			try {
				fileObjectID = template.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metadata);
			} catch (IOException e) {
				log.error("파일 업로드 실패 {}", e);
				throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
			}

			log.error("file.getOriginalFilename() is {}", file.getOriginalFilename());

			String fullpath = directoryPath + File.separator + file.getOriginalFilename();				

			try {
				File destFile = new File(fullpath);
				file.transferTo(destFile);
				log.info("File uploaded successfully!");
			} catch (IOException e) {
				log.info("File upload failed: " + e.getMessage());
		}
			
			String fileUrl = "/api/v1/file/download/" + fileObjectID.toString();
            // 파일 객체 ID 저장
			fileUrls.add(fileUrl);

			FileEntity fileEntity = FileEntity.builder()
					.id(fileObjectID.toString())
					.name(file.getOriginalFilename())
					.size(FormatConverter.convertInputBytes(file.getSize()))
					.type(file.getContentType())
					.path(fullpath)
					.url(fileUrl)
					.userName(userName)
					.orgName(orgName)
					.created_at(Instant.now())
					.build();

			fileRepository.save(fileEntity);
		}
		return fileUrls;
	}

	@Override
    public FileEntity.LoadFile downloadFile(String id) throws IllegalStateException, IOException {

		GridFSFile gridFSFile = template.findOne( new Query(Criteria.where("_id").is(id)) );

		FileEntity.LoadFile fileEntity = new FileEntity.LoadFile();

		if (gridFSFile != null && gridFSFile.getMetadata() != null) {

			log.error("fsFile.getMetadata() = {}", gridFSFile.getMetadata());

				fileEntity.setName( gridFSFile.getFilename() );
				fileEntity.setSize( gridFSFile.getMetadata().get("size").toString() );
				fileEntity.setType( gridFSFile.getMetadata().get("type").toString() );

				fileEntity.setFile( IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()) );
		} else {
			throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
		}

		return fileEntity;
	}

	public Map<String, Object> downloadFiles(String key, String value) {
  		List<GridFSFile> allFiles = new ArrayList<>();
			GridFSFindIterable resources = template.find(new Query().addCriteria(Criteria.where("metadata." + key).is(value)));
			resources.forEach((Consumer<GridFSFile>) file -> allFiles.add(file));

			log.error("allFiles {}", allFiles);
			for(GridFSFile file : allFiles) {
				log.error("FileName : {}", file.getFilename());
			}

			try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream)) {
					for (GridFSFile file : allFiles) {

							ZipEntry zipEntry = new ZipEntry(file.getFilename());
							zipOut.putNextEntry(zipEntry);

							byte[] objectData = IOUtils.toByteArray(operations.getResource(file).getInputStream());
							zipOut.write(objectData);
							zipOut.closeEntry();
					}
					zipOut.finish();

			Map<String, Object> returnVal = new HashMap<>();
			returnVal.put("name", value + ".zip");
			returnVal.put("data", byteArrayOutputStream.toByteArray());
			
			return returnVal;

			} catch (IOException e) {
				log.error("Error creating and sending the zip file: {}", e);
				return null;
			}
	}

	public void deleteById(String id) {

		FileEntity fileEntity = findById(id);
		folderChk("rmdir", fileEntity.getPath());

		fileRepository.delete(fileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("File with Id " + id + " Not Found.")));
	}


	@Transactional(readOnly = true)
    public Page<FileEntity> findPageByName(String name, Pageable pageable) {
		return fileRepository.findPageByName(name, pageable);
	}

	@Transactional(readOnly = true)
    public FileEntity findById(String id) {
		return fileRepository.findByid(id).orElseThrow(() -> new EntityNotFoundException("file with Id " + id + " Not Found."));
	}

	@Transactional(readOnly = true)
    public FileEntity findByPath(String path) {
		return fileRepository.findByPath(path).orElseThrow(() -> new EntityNotFoundException("file with Path " + path + " Not Found."));
	}

	public void deleteFiles(String name, List<String> files) {

		String filePath = null;

		for (String file : files){
			
			String[] splitText = file.split("/");
			String objectId = splitText[splitText.length -1];

			GridFSFile gridFSFile = template.findOne( new Query(Criteria.where("_id").is(objectId)));

			filePath = gridFSFile.getMetadata().get("path").toString();
			String fileName = gridFSFile.getMetadata().get("name").toString();
			String fullPath = filePath + File.separator + fileName;

			template.delete(new Query(Criteria.where("_id").is(objectId)));

			fileRepository.delete(fileRepository.findByPath(fullPath).orElseThrow(() -> new EntityNotFoundException("File with Path " + file + " Not Found.")));
		}

			if(name != null) {
				folderChk("rmdir", filePath);
			}
	}
	
	public String folderChk(String type, String path) {

		if(!new File(uploadPath).exists()) {
			log.error("Folder 생성 실패, 루트 폴더를 수동으로 생성해주세요 : {}", uploadPath);
			throw new CustomException(ErrorCode.INVALID_USERNAME);
		} else {

			if (type == "mkdir") {

					// 업로드 디렉토리 생성 (만약 존재하지 않는 경우)
					Path directoryPath = Paths.get(uploadPath + File.separator + path);
					try {
							// 디렉토리 생성
							Files.createDirectories(directoryPath,
								PosixFilePermissions.asFileAttribute(      
									PosixFilePermissions.fromString("rwxr-x---")
								));;
							System.out.println(directoryPath.toString() + " 디렉토리가 생성되었습니다.");
							return directoryPath.toString();
					} catch (IOException e) {
							log.error("디렉토리 생성 실패 : {}", e);
					}

			} else if (type == "rmdir") {
				log.error("폴더 삭제");

					// 업로드 디렉토리 생성 (만약 존재하지 않는 경우)
					Path directoryPath = Paths.get(path);
				log.error("directoryPath.toString() is {}", directoryPath.toString());
					File folder = new File(directoryPath.toString());
					// 디렉토리 내 파일 삭제
					while (folder.exists()) {
							File[] folder_list = folder.listFiles(); // 파일리스트 얻어오기

							for (int j = 0; j < folder_list.length; j++) {
									folder_list[j].delete(); // 파일 삭제
									System.out.println("파일이 삭제되었습니다.");
							}

							if (folder_list.length == 0 && folder.isDirectory()) {
									folder.delete(); // 대상폴더 삭제
									System.out.println("폴더가 삭제되었습니다.");
							}
					}
					return "";
			}

		}
		return "";
}
}