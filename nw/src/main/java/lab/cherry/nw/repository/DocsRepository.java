package lab.cherry.nw.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import lab.cherry.nw.model.DocsEntity;

/**
 * <pre>
 * ClassName : QsheetRepository
 * Type : interface
 * Descrption : 큐시트 JPA 구현을 위한 인터페이스입니다.
 * Related : QsheetServiceImpl, QsheetService
 * </pre>
 */
public interface DocsRepository extends MongoRepository<DocsEntity, String> {

    Page<DocsEntity> findAll(Pageable pageable);
    Optional<DocsEntity> findById(String id);
    void deleteById(UUID id);
    
}