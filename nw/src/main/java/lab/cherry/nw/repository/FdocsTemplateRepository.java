package lab.cherry.nw.repository;

import lab.cherry.nw.model.FdocsTemplateEntity;
import lab.cherry.nw.model.FinaldocsEntity;
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
public interface FdocsTemplateRepository extends MongoRepository<FdocsTemplateEntity, UUID> {

    Page<FdocsTemplateEntity> findAll(Pageable pageable);

    Page<FdocsTemplateEntity> findPageByName(String fdocsTemplateName, Pageable pageable);

    Page<FdocsTemplateEntity> findPageById(String id, Pageable pageable);

    Optional<FdocsTemplateEntity> findById(String id);

    Optional<FdocsTemplateEntity> findByName(String fdocsTemplateName);

    List<FdocsTemplateEntity> findAllById(List<String> orgIds);
}