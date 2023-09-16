package lab.cherry.nw.service.Impl;

import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import lab.cherry.nw.model.FileEntity;
import lab.cherry.nw.repository.FileRepository;
import lab.cherry.nw.service.FileService;
import lab.cherry.nw.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

	private final MinioClient minioClient;
    private final FileRepository fileRepository;
	private final GridFsTemplate gridFsTemplate;
	private final UserService userService;
	

	public boolean isMinioConnected() {
		try {
			// 버킷 목록을 가져와서 Minio 연결 상태 확인
			minioClient.listBuckets();
			return true; // 연결이 성공적으로 확인됨
		} catch (Exception e) {
			// Minio 연결 예외 처리
			return false; // 연결 실패
		}
	}

	/**
     * [WeddinghallServiceImpl] 전체 웨딩홀(예식장) 조회 함수
     *
     * @return DB에서 전체 웨딩홀(예식장) 정보 목록을 리턴합니다.
     * <pre>
     * 전체 웨딩홀(예식장)를 조회하여, 웨딩홀(예식장) 정보 목록을 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    @Override
    public Page<FileEntity> getFiles(Pageable pageable) {
		return fileRepository.findAll(pageable);
		//        return EntityNotFoundException.requireNotEmpty(weddinghallRepository.findAll(pageable), "Weddinghalls Not Found");
	}
	
	
	@Override
    public List<String> uploadFiles(Map<String, String> info, List<MultipartFile> files) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException {
		
		log.error("[*0] in uploadFiles");
		
		String bucketName = info.get("org");	// 조직명
		String userName = info.get("user");
		
		List<String> fileObjectIds = new ArrayList<>();
//
//		try {
//			
//			log.error("Connect Check : {}", isMinioConnected());
//			
//			// Bucket이 존재하지 않으면 생성
//			if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
//				minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
//			}
//
//			for (MultipartFile file : files) {
//				String originalFilename = file.getOriginalFilename();
//				String objectName = (userName != null && !userName.isEmpty()) ?
//                   orgName + "/" + userName + "/" + originalFilename : 
//                   orgName + "/" + originalFilename;
//
//				// 파일 업로드
//				minioClient.putObject(
//						PutObjectArgs.builder()
//                        .bucket(bucketName)
//                        .object(objectName)
//                        .contentType(file.getContentType())
//                        .stream(file.getInputStream(), file.getSize(), -1)
//                        .build()
//				);
//
//				// 파일 객체 ID 저장
//				fileObjectIds.add(objectName);
//			}
//		} catch (Exception e) {
//			// Minio 연결 예외 처리
//			throw new MinioException("Minio 연결 또는 파일 업로드 중 오류 발생");
//		}

		return fileObjectIds;
	}

    @Override
    public Optional<GridFSFile> getFileById(ObjectId fileId) {
		Query query = new Query(Criteria.where("_id").is(fileId));
		return Optional.ofNullable(gridFsTemplate.findOne(query));
	}

	@Override
	public Optional<GridFSFile> getFileByName(String filename) {
		Query query = new Query(Criteria.where("filename").is(filename));
		return Optional.ofNullable(gridFsTemplate.findOne(query));
	}
	
    @Override
    public GridFSFindIterable getAllFiles() {
		Query query = new Query();
		return gridFsTemplate.find(query);
	}

    @Override
    public void deleteFile(ObjectId fileId) {
		Query query = new Query(Criteria.where("_id").is(fileId));
		gridFsTemplate.delete(query);
    }

	@Override
	public GridFsResource getResource(GridFSFile gridFSFile) {
		return gridFsTemplate.getResource(gridFSFile);
	}
	
	@Override
    public HttpHeaders downloadFile(String fileId) {
		GridFsResource resource = gridFsTemplate.getResource(fileId);

		// 파일의 내용을 byte 배열로 읽어옵니다.
        byte[] fileData = new byte[0];
        try {
            fileData = new byte[(int) resource.contentLength()];
			resource.getInputStream().read(fileData);
		} catch (IOException e) {
			log.error("파일 콘텐츠 확인 중 오류 발생: {}", e.getMessage());
			throw new RuntimeException("파일 콘텐츠 확인 중 오류 발생", e);
        }

		// HTTP 응답 헤더에 파일 다운로드 정보를 설정합니다.
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=" + resource.getFilename());
		
		return headers;		
	}


	@Transactional(readOnly = true)
    public Page<FileEntity> findPageByName(String name, Pageable pageable) {
		return fileRepository.findPageByName(name, pageable);
	}
}