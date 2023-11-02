package lab.cherry.nw.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import lab.cherry.nw.model.QsheetHistoryEntity;

/**
 * <pre>
 * ClassName : QsheetHistoryRepository
 * Type : interface
 * Descrption : 큐시트 히스토리 JPA 구현을 위한 인터페이스입니다.
 * Related : QsheetHistoryServiceImpl, QsheetHistoryService
 * </pre>
 */
public interface QsheetHistoryRepository extends MongoRepository<QsheetHistoryEntity, String> {

    Page<QsheetHistoryEntity> findAll(Pageable pageable);

    @Query("{'user.$_id' : ?0}")
    Page<QsheetHistoryEntity> findPageByUserid(String userid, Pageable pageable);

    @Query("{'qsheet.$_id' : ?0}")
    Page<QsheetHistoryEntity> findPageByQsheetid(String qsheetid, Pageable pageable);

    Optional<QsheetHistoryEntity> findById(String id);

    @Query("{'qsheet.$_id' : ?0}")
    List<QsheetHistoryEntity> findByQsheetId(ObjectId qsheetId);


    void deleteById(UUID id);
}