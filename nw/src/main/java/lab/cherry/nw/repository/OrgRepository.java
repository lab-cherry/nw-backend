package lab.cherry.nw.repository;

import lab.cherry.nw.model.OrgEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


/**
 * <pre>
 * ClassName : OrgRepository
 * Type : interface
 * Descrption : 조직 JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
//@Repository
public interface OrgRepository extends MongoRepository<OrgEntity, UUID> {

    Page<OrgEntity> findAll(Pageable pageable);

    Page<OrgEntity> findPageByName(String orgname, Pageable pageable);

    Optional<OrgEntity> findById(String id);

    Optional<OrgEntity> findByName(String orgname);

    void deleteById(UUID id);

}