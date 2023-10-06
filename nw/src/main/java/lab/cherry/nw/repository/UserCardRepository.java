package lab.cherry.nw.repository;

import lab.cherry.nw.model.OrgEntity;
import lab.cherry.nw.model.UserCardEntity;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * <pre>
 * ClassName : UserCardRepository
 * Type : interface
 * Descrption : 고객카드 JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
//@Repository
public interface UserCardRepository extends MongoRepository<UserCardEntity, UUID> {

    Page<UserCardEntity> findAll(Pageable pageable);

    Page<UserCardEntity> findPageById(String id, Pageable pageable);

    Optional<UserCardEntity> findById(String id);

    List<UserCardEntity> findAllById(List<String> orgIds);
}
