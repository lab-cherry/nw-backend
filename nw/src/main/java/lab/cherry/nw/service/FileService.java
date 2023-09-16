package lab.cherry.nw.service;

import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import io.minio.errors.MinioException;
import lab.cherry.nw.model.FileEntity;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * <pre>
 * ClassName : FileService
 * Type : interface
 * Description : 파일 관리와 관련된 함수를 정리한 인터페이스입니다.
 * Related : FileRepsitory, FileServiceImpl
 * </pre>
 */
@Component
public interface FileService {

	Page<FileEntity> getFiles(Pageable pageable);
	List<String> uploadFiles(Map<String, String> info, List<MultipartFile> files) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException;
	Optional<GridFSFile> getFileById(ObjectId fileId);
	Optional<GridFSFile> getFileByName(String filename);
	GridFSFindIterable getAllFiles();
	void deleteFile(ObjectId fileId);
	GridFsResource getResource(GridFSFile gridFSFile);
	HttpHeaders downloadFile(String fileId);
	Page<FileEntity> findPageByName(String name, Pageable pageable);
}