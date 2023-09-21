package lab.cherry.nw.repository;

import lab.cherry.nw.model.FinalTemplEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * <pre>
 * ClassName : FinaldocsRepository
 * Type : interface
 * Descrption : 최종 확인서 JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
//@Repository
public interface FinalTemplRepository extends MongoRepository<FinalTemplEntity, UUID> {

    Page<FinalTemplEntity> findAll(Pageable pageable);

    Page<FinalTemplEntity> findPageByName(String fdocsTemplateName, Pageable pageable);

    Page<FinalTemplEntity> findPageById(String id, Pageable pageable);

    Optional<FinalTemplEntity> findById(String id);

    Optional<FinalTemplEntity> findByName(String fdocsTemplateName);

    List<FinalTemplEntity> findAllById(List<String> orgIds);
}