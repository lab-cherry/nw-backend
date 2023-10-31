package lab.cherry.nw.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lab.cherry.nw.error.ErrorResponse;
import lab.cherry.nw.error.ResultResponse;
import lab.cherry.nw.error.enums.SuccessCode;
import lab.cherry.nw.model.EventEntity;
import lab.cherry.nw.service.EventService;
import lab.cherry.nw.util.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * ClassName : EventController
 * Type : class
 * Description : 이벤트 목록 조회, 이벤트 상세 조회, 이벤트 생성(업데이트), 이벤트 삭제 등 이벤트와 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : UserRepository, UserService, UserServiceImpl
 * </pre>
 */

// MEMO : 재사용성을 위해 ServiceImpl에서만 비즈니스 로직을 사용하기로 함
//        Dto를 통해 알맞는 파라미터로 데이터 가공 후 사용하기로 함
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
@Tag(name = "Event", description = "Event API Document")
public class EventController {
    
    private final EventService eventService;
    
    /**
     * [EventController] 이벤트 목록 함수
     *
     * @return 전체 이벤트 목록을 반환합니다.
     *
     * Author : taking(taking@duck.com)
     */
    @GetMapping("")
    @Operation(summary = "이벤트 목록", description = "이벤트 목록을 조회합니다.")
    public ResponseEntity<?> findAllEvents(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        log.info("retrieve all event controller...!");

        Pageable pageable = PageRequest.of(page, size, Sort.by(Common.getOrder(sort)));

        Page<EventEntity> eventEntity = eventService.getEvents(pageable);

        //        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.getUsers());
        return new ResponseEntity<>(eventEntity, new HttpHeaders(), HttpStatus.OK);
    }
    
    /**
     * [EventController] 이벤트 생성 함수
     *
     * @param id usercard의 고유번호입니다.
     * @param eventType 이벤트 타입입니다. (ex. "reserve", "wedding")
     * @return
     * <pre>
     * true  : 성공(200)을 반환합니다.
     * false : 에러(400)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @PostMapping(value = "{id}")
    @Operation(summary = "이벤트 생성", description = "이벤트을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 생성이 완료되었습니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "입력 값이 잘못되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> createEvent(@PathVariable("id") String id) {

		log.info("[EventController] createEvent...!");

        return new ResponseEntity<>(eventService.createEvent(id), new HttpHeaders(), HttpStatus.OK);
    }
	
	/**
     * [EventController] 특정 이벤트 삭제 함수
     *
     * @param id 이벤트 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 이벤트를 삭제처리합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @DeleteMapping("{id}")
    @Operation(summary = "이벤트 삭제", description = "이벤트를 삭제합니다.")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") String id) {

		log.info("[EventController] deleteEvent...!");

		eventService.deleteById(id);

		final ResultResponse response = ResultResponse.of(SuccessCode.OK);
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
	}


    /**
     * [EventController] 특정 이벤트 조회 함수
     *
     * @param id 이벤트 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 이벤트 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * How-to:
     *  /api/v1/event/{id}
     *
     * Author : taking(taking@duck.com)
     */
    @GetMapping("{id}")
    @Operation(summary = "이벤트 찾기", description = "이벤트를 조회합니다.")
    public ResponseEntity<?> findEvent(
            @PathVariable("id") String id) {

        log.info("[EventController] findEvent...!");

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.findById(id));
        return new ResponseEntity<>(eventService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

}