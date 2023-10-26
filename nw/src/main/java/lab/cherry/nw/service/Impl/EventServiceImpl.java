package lab.cherry.nw.service.Impl;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.OrgEntity;
import lab.cherry.nw.model.UserCardEntity;
import lab.cherry.nw.model.WeddinghallEntity;
import lab.cherry.nw.model.EventEntity;
import lab.cherry.nw.repository.EventRepository;
import lab.cherry.nw.service.FileService;
import lab.cherry.nw.service.OrgService;
import lab.cherry.nw.service.UserCardService;
import lab.cherry.nw.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lab.cherry.nw.util.FormatConverter;

/**
 * <pre>
 * ClassName : EventServiceImpl
 * Type : class
 * Description : 캘린더 내 이벤트와 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-mongo
 * </pre>
 */
@Slf4j
@Service("eventServiceImpl")
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
	
	private final EventRepository eventRepository;
	private final FileService fileService;
  private final UserCardService userCardService;
	
	/**
     * [EventServiceImpl] 전체 이벤트 조회 함수
     *
     * @return DB에서 전체 이벤트 정보 목록을 리턴합니다.
     * <pre>
     * 전체 이벤트를 조회하여, 이벤트 정보 목록을 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    @Override
    public Page<EventEntity> getEvents(Pageable pageable) {
		return eventRepository.findAll(pageable);
		//        return EntityNotFoundException.requireNotEmpty(weddinghallRepository.findAll(pageable), "Weddinghalls Not Found");
	}
	

	/**
     * [EventServiceImpl] 이벤트 생성 함수
     *
     * @param id 이벤트 생성에 필요한 usercard 고유번호를 담은 개체입니다.
     * @return 생성된 이벤트 정보를 리턴합니다.
     * <pre>
     * 이벤트를 등록합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public EventEntity createEvent(String id, String eventType) {

		UserCardEntity UserCardEntity = userCardService.findById(id);
    Map<String, Integer> dateFormat = new HashMap<>();
    Instant EventDate = Instant.now();
    Integer Hour = null;
    Integer Minute = null;

    if(eventType == "reserve") {
      
			dateFormat = FormatConverter.convertDateFormat(UserCardEntity.getResDate());
			Hour = dateFormat.get("hour");
      Minute = dateFormat.get("minute");
      EventDate = Instant.parse(UserCardEntity.getResDate());

    } else if(eventType == "wedding") {
      
			dateFormat = FormatConverter.convertDateFormat(UserCardEntity.getWeddingDate());
			Hour = dateFormat.get("hour");
      Minute = dateFormat.get("minute");
      EventDate = Instant.parse(UserCardEntity.getWeddingDate());

    }

    String eventName = Hour + ":" + Minute + " " + UserCardEntity.getGroom() + "♥" + UserCardEntity.getBride();
    log.error("eventName {}", eventName);

    // {hour}:{min} {groom}♥{bride}
		checkExistsWithEventName(eventName);	// 중복 체크
    
    EventEntity eventEntity = EventEntity.builder()
        .id(id)
        .title(eventName)
        // .description()
        // .location(UserCardEntity)
        .date(EventDate)
        .created_at(Instant.now())
        .build();

		return eventRepository.save(eventEntity);
	}


	@Transactional(readOnly = true)
    public Page<EventEntity> findPageByName(String name, Pageable pageable) {
		  return eventRepository.findPageByName(name, pageable);
	}

	/**
     * [EventServiceImpl] 이벤트 이름 중복 체크 함수
     *
     * @param name 중복 체크에 필요한 이벤트 이름 객체입니다.
     * @throws CustomException 이벤트 이름이 중복된 경우 예외 처리 발생
     * <pre>
     * 입력된 이벤트 이름으로 이미 등록된 조직이 있는지 확인합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public void checkExistsWithEventName(String name) {
		if (eventRepository.findByName(name).isPresent()) {
			throw new CustomException(ErrorCode.DUPLICATE); // 이벤트 이름이 중복됨
		}
	}

	/**
     * [EventServiceImpl] ID로 이벤트 조회 함수
     *
     * @param id 조회할 이벤트의 식별자입니다.
     * @return 주어진 식별자에 해당하는 이벤트 정보
     * @throws EntityNotFoundException 해당 ID의 이벤트 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id에 해당하는 이벤트 정보를 조회합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public EventEntity findById(String id) {
		  return eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event with Id " + id + " Not Found."));
	}
	

	/**
     * [EventServiceImpl] 이벤트 삭제 함수
     *
     * @param id 삭제할 이벤트의 식별자입니다.
     * @throws EntityNotFoundException 해당 ID의 이벤트 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id를 가진 이벤트 정보를 삭제합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public void deleteById(String id) {
      EventEntity eventEntity = findById(id);

		  eventRepository.delete(eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event with Id " + id + " Not Found.")));
	}

}