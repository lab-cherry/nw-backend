package lab.cherry.nw.service;

import lab.cherry.nw.model.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


/**
 * <pre>
 * ClassName : EventService
 * Type : interface
 * Description : 켈린더 내 이벤트와 관련된 함수를 정리한 인터페이스입니다.
 * Related : EventController, EventServiceImpl
 * </pre>
 */
@Component
public interface EventService {
    Page<EventEntity> getEvents(Pageable pageable);
	EventEntity createEvent(String id);
    EventEntity findById(String id);
    void deleteById(String id);
    boolean checkExistsWithEventId(String id);
}
