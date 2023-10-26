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
 * ClassName : QsheetRepository
 * Type : interface
 * Descrption : 큐시트히스토리 JPA 구현을 위한 인터페이스입니다.
 * Related : QsheetServiceImpl, QsheetService
 * </pre>
 */
public interface QsheetHistoryRepository extends MongoRepository<QsheetHistoryEntity, String> {

    Page<QsheetHistoryEntity> findAll(Pageable pageable);

    Page<QsheetHistoryEntity> findPageByUserid(String userid, Pageable pageable);

    Page<QsheetHistoryEntity> findPageByQsheetid(String qsheetid, Pageable pageable);

    Optional<QsheetHistoryEntity> findById(String id);

    @Query("{'qsheetid.$id' : ?0}")
    List<QsheetHistoryEntity> findByQsheetId(ObjectId qsheetId);
    
    // @Query("{'userid.$id' : ?0}")
    // Optional<QsheetHistoryEntity> findByUserid(ObjectId userId);
  

    void deleteById(UUID id);
}