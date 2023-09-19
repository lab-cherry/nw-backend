package lab.cherry.nw.repository;

import lab.cherry.nw.model.BookmarkEntity;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;
import java.util.UUID;


/**
 * <pre>
 * ClassName : BookmarkRepository
 * Type : interface
 * Descrption : 북마크 JPA 구현을 위한 인터페이스입니다.
 * Related : BookmarkServiceImpl, BookmarkService
 * </pre>
 */
public interface BookmarkRepository extends MongoRepository<BookmarkEntity, UUID> {

    Page<BookmarkEntity> findAll(Pageable pageable);

    Page<BookmarkEntity> findPageByUserid(String userid, Pageable pageable);

    Optional<BookmarkEntity> findById(String id);
    
    @Query("{'userid.$id' : ?0}")
    Optional<BookmarkEntity> findByUserid(ObjectId userId);
  

    void deleteById(UUID id);
}