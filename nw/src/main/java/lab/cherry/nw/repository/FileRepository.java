package lab.cherry.nw.repository;

import lab.cherry.nw.model.FileEntity;
import lab.cherry.nw.model.OrgEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


/**
 * <pre>
 * ClassName : FileRepository
 * Type : interface
 * Descrption : 파일 JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-mongo
 * </pre>
 */
//@Repository
public interface FileRepository extends MongoRepository<FileEntity, String> {

	Page<FileEntity> findAll(Pageable pageable);

	Page<FileEntity> findPageByName(String filename, Pageable pageable);

	Optional<FileEntity> findById(String id);

	Optional<FileEntity> findByName(String orgname);

	List<FileEntity> findAllById(List<String> orgIds);
}