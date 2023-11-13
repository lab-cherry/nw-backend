package lab.cherry.nw.service.Impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.compress.utils.IOUtils;
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
import org.bson.types.ObjectId;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

  private final FileRepository fileRepository;	
	private final GridFsTemplate template;
	private final GridFsOperations operations;

	public Page<FileEntity> getFiles(Pageable pageable) {
		return fileRepository.findAll(pageable);
	}
	
	@Override
    public List<String> uploadFiles(String seq, List<MultipartFile> files) {

		List<String> fileUrls = new ArrayList<>();
		for (MultipartFile file : files) {		

			DBObject metadata = new BasicDBObject();
			metadata.put("seq", seq);
			metadata.put("name", file.getOriginalFilename());
			metadata.put("size", file.getSize());
			metadata.put("type", file.getContentType());

			Object fileObjectID = null;
			try {
				fileObjectID = template.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metadata);
			} catch (IOException e) {
				log.error("파일 업로드 실패 {}", e);
				throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
			}

			String fileUrl = "/api/v1/file/download/" + fileObjectID.toString();
            // 파일 객체 ID 저장
			fileUrls.add(fileUrl);

			FileEntity fileEntity = FileEntity.builder()
					.id(fileObjectID.toString())
					.name(file.getOriginalFilename())
					.size(FormatConverter.convertInputBytes(file.getSize()))
					.type(file.getContentType())
					.url(fileUrl)
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
			log.error("allFiles.size {}", allFiles.size());

			try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream)) {
					for (GridFSFile file : allFiles) {

							int lastIndex = file.getFilename().lastIndexOf(".");
							String fileNameWithoutExtension = file.getFilename().substring(0, lastIndex);
							String Extension = file.getFilename().substring(file.getFilename().lastIndexOf(".") + 1);
							String fullName = fileNameWithoutExtension + "-" + new ObjectId() + "." + Extension;

							ZipEntry zipEntry = new ZipEntry(fullName);
							zipEntry.setSize(file.getChunkSize());
							zipOut.putNextEntry(zipEntry);

							byte[] objectData = IOUtils.toByteArray(operations.getResource(file).getInputStream());
							zipOut.write(objectData);
							zipOut.closeEntry();
					}
					zipOut.finish();
					zipOut.close();

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
    public FileEntity findByName(String name) {
		return fileRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("file with Name " + name + " Not Found."));
	}

	public void deleteFiles(List<String> files) {

		for (String file : files){
			
			String[] splitText = file.split("/");
			String objectId = splitText[splitText.length -1];

			GridFSFile gridFSFile = template.findOne( new Query(Criteria.where("_id").is(objectId)));
			String fileName = gridFSFile.getMetadata().get("name").toString();

			template.delete(new Query(Criteria.where("_id").is(objectId)));

			fileRepository.delete(fileRepository.findByName(fileName).orElseThrow(() -> new EntityNotFoundException("File with Path " + file + " Not Found.")));
		}
	}
}