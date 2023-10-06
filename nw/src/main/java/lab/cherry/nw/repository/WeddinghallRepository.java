package lab.cherry.nw.repository;

import lab.cherry.nw.model.WeddinghallEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

/**
 * <pre>
 * ClassName : WeddinghallRepository
 * Type : interface
 * Descrption : 웨딩홀(예식장) 관리 JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-mongo
 * </pre>
 */
//@Repository
public interface WeddinghallRepository extends MongoRepository<WeddinghallEntity, String> {

	Page<WeddinghallEntity> findPageByName(String weddinghallname, Pageable pageable);
	Optional<WeddinghallEntity> findByName(String name);
    
}