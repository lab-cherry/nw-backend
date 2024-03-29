package lab.cherry.nw.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import lab.cherry.nw.model.EventEntity;

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

    Optional<EventEntity> findById(String id);
    Optional<EventEntity> findByTitle(String title);
    
}