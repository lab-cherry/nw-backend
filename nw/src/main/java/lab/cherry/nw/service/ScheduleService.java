package lab.cherry.nw.service;

import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import lab.cherry.nw.model.ScheduleEntity;

/**
 * <pre>
 * ClassName : ScheduleService
 * Type : interface
 * Description : 스케줄표와 관련된 함수를 정리한 인터페이스입니다.
 * Related : ScheduleController, ScheduleServiceImpl
 * </pre>
 */
@Component
public interface ScheduleService {
    Page<ScheduleEntity> getSchedule(Pageable pageable);
	ScheduleEntity  scheduleByDate(Instant fromDate, Instant toDate);
	ScheduleEntity transColumn(ScheduleEntity.transDto scheduleTransDto);
	ScheduleEntity findById(String id);
	ScheduleEntity findByName(String name);
    void deleteById(String id);
    Page<ScheduleEntity> findPageByName(String name, Pageable pageable);
    Page<ScheduleEntity> findPageById(String id, Pageable pageable);
}
