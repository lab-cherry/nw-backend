package lab.cherry.nw.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import lab.cherry.nw.model.BookmarkEntity;
import lab.cherry.nw.model.FinaldocsEntity;
import lab.cherry.nw.model.UserCardEntity;


/**
 * <pre>
 * ClassName : FinaldocsRepository
 * Type : interface
 * Descrption : 최종 확인서 JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
//@Repository
public interface FinaldocsRepository extends MongoRepository<FinaldocsEntity, String> {

    Page<FinaldocsEntity> findAll(Pageable pageable);

    // Page<FinaldocsEntity> findPageByName(String finaldocsName, Pageable pageable);

    Page<FinaldocsEntity> findPageById(String id, Pageable pageable);

    Optional<FinaldocsEntity> findById(String id);

    // Optional<FinaldocsEntity> findByName(String finaldocsName);

	List<FinaldocsEntity> findAllBycreatedAtBetween(Instant start, Instant end);

    @Query("{'user.$_id' : ?0}")
    Optional<FinaldocsEntity> findByUserid(String userid);
  
}