package lab.cherry.nw.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import lab.cherry.nw.model.UserCardEntity;


/**
 * <pre>
 * ClassName : UserCardRepository
 * Type : interface
 * Descrption : 고객카드 JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
//@Repository
public interface UserCardRepository extends MongoRepository<UserCardEntity, String> {

    Page<UserCardEntity> findAll(Pageable pageable);

    Page<UserCardEntity> findPageById(String id, Pageable pageable);

    Optional<UserCardEntity> findById(String id);

    String findByUserinfo(String id);

    List<UserCardEntity> findAllById(List<String> orgIds);

    @Query("{'userinfo.$_id' : ?0}")
    Optional<UserCardEntity> findByUserSeq(String seq);
}
