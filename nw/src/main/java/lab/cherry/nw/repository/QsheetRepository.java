package lab.cherry.nw.repository;

import lab.cherry.nw.model.QsheetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

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
    Page<QsheetEntity> findPageByUserid(String userid, Pageable pageable);
    Page<QsheetEntity> findPageByOrgid(String orgid, Pageable pageable);

    Optional<QsheetEntity> findById(String id);

    Optional<QsheetEntity> findByuserid(String userid);
    void deleteById(UUID id);
}