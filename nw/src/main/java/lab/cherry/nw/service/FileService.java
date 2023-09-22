package lab.cherry.nw.service;

import lab.cherry.nw.model.FileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

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

	List<String> uploadFiles(Map<String, String> info, List<MultipartFile> files);
	Page<FileEntity> getFiles(Pageable pageable);
	Page<FileEntity> findPageByName(String name, Pageable pageable);
	FileEntity findById(String id);
	FileEntity findByPath(String path);
	void deleteById(String id);
	InputStream downloadFile(String orgId, String path);
}