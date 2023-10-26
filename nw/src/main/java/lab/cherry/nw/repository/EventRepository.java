package lab.cherry.nw.repository;

import lab.cherry.nw.model.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

/**
 * <pre>
 * ClassName : EventRepository
 * Type : interface
 * Descrption : 캘린더 내 이벤트 관리 JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-mongo
 * </pre>
 */
//@Repository
public interface EventRepository extends MongoRepository<EventEntity, String> {

	Page<EventEntity> findPageByName(String eventname, Pageable pageable);
	Optional<EventEntity> findByName(String name);
    
}