package lab.cherry.nw.service;

import lab.cherry.nw.model.FinalTemplEntity;
import lab.cherry.nw.model.ScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

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
    Page<FinalTemplEntity> getFinalTemplate(Pageable pageable);
	ScheduleEntity transColumn(ScheduleEntity.transDto scheduleTransDto);
    void updateById(String id, FinalTemplEntity.FinalTemplUpdateDto org);
    FinalTemplEntity findById(String id);
    FinalTemplEntity findByName(String name);
    void deleteById(String id);
    Page<FinalTemplEntity> findPageByName(String name, Pageable pageable);
    Page<FinalTemplEntity> findPageById(String id, Pageable pageable);
}
