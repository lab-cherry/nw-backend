package lab.cherry.nw.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import lab.cherry.nw.model.ScheduleEntity;


/**
 * <pre>
 * ClassName : ScheduleRepository
 * Type : interface
 * Descrption : 스케줄표 JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
//@Repository
public interface ScheduleRepository extends MongoRepository<ScheduleEntity, String> {

    Page<ScheduleEntity> findAll(Pageable pageable);

    // Page<ScheduleEntity> findPageByName(String scheduleName, Pageable pageable);

    Page<ScheduleEntity> findPageById(String id, Pageable pageable);

    Optional<ScheduleEntity> findById(String id);

    // Optional<ScheduleEntity> findByName(String scheduleName);

    List<ScheduleEntity> findAllById(List<String> scheduleIds);
}