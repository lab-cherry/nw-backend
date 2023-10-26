package lab.cherry.nw.repository;

import lab.cherry.nw.model.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * <pre>
 * ClassName : TagRepository
 * Type : interface
 * Descrption : 게시판 태그 JPA 구현을 위한 인터페이스입니다.
 * Related : TagServiceImpl, TagService
 * </pre>
 */
public interface TagRepository extends MongoRepository<TagEntity, String> {

    Page<TagEntity> findAll(Pageable pageable);
	Optional<TagEntity> findByName(String name);
    Optional<TagEntity> findById(String id);
    void deleteById(UUID id);
}