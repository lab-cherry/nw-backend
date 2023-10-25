package lab.cherry.nw.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import lab.cherry.nw.model.OrgEntity;


/**
 * <pre>
 * ClassName : OrgRepository
 * Type : interface
 * Descrption : 조직 JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-mongo
 * </pre>
 */
//@Repository
public interface OrgRepository extends MongoRepository<OrgEntity, String> {

    Page<OrgEntity> findAll(Pageable pageable);

    Page<OrgEntity> findPageByName(String orgname, Pageable pageable);

    Optional<OrgEntity> findByid(String id);

    Optional<OrgEntity> findByName(String orgname);

	List<OrgEntity> findAllById(String orgId);
}