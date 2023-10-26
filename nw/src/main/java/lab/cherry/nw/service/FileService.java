package lab.cherry.nw.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import lab.cherry.nw.model.FileEntity;

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

	List<String> uploadFiles(String seq, List<MultipartFile> files);
	Page<FileEntity> getFiles(Pageable pageable);
	Page<FileEntity> findPageByName(String name, Pageable pageable);
	FileEntity findById(String id);
	FileEntity findByName(String nme);
	void deleteById(String id);

	FileEntity.LoadFile downloadFile(String id) throws IllegalStateException, IOException;
	Map<String, Object> downloadFiles(String key, String value);
	// byte[] downloadZip(String bucketName, String objectName);
	void deleteFiles(List<String> files);
}