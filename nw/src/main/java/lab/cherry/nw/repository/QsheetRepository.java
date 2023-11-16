package lab.cherry.nw.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import lab.cherry.nw.model.QsheetEntity;
import lab.cherry.nw.model.RoleEntity;

/**
 * <pre>
 * ClassName : QsheetRepository
 * Type : interface
 * Descrption : 큐시트 JPA 구현을 위한 인터페이스입니다.
 * Related : QsheetServiceImpl, QsheetService
 * </pre>
 */
public interface QsheetRepository extends MongoRepository<QsheetEntity, String> {

    Page<QsheetEntity> findAll(Pageable pageable);

    @Query("{'user.$_id' : ?0, 'type' : ?1 }")
    Page<QsheetEntity> findPageByUserid(String userSeq, String type, Pageable pageable);
    @Query("{ 'org.$_id' : ?0 ,'type' : ?1 }")
    Page<QsheetEntity> findPageByOrgid(String orgSeq, String type, Pageable pageable);

    Optional<QsheetEntity> findById(String id);
    void deleteById(UUID id);
}