package lab.cherry.nw.repository;

import lab.cherry.nw.model.ScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * <pre>
 * ClassName : ScheduleRepository
 * Type : interface
 * Descrption : 최종 확인서 JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
//@Repository
public interface ScheduleRepository extends MongoRepository<ScheduleEntity, UUID> {

    Page<ScheduleEntity> findAll(Pageable pageable);

    Page<ScheduleEntity> findPageByName(String scheduleName, Pageable pageable);

    Page<ScheduleEntity> findPageById(String id, Pageable pageable);

    Optional<ScheduleEntity> findById(String id);

    Optional<ScheduleEntity> findByName(String scheduleName);

//	Optional<ScheduleEntity> findByDate(String weddingDate);

    List<ScheduleEntity> findAllById(List<String> scheduleIds);
}
