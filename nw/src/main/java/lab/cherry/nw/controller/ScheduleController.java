package lab.cherry.nw.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lab.cherry.nw.error.ErrorResponse;
import lab.cherry.nw.model.ScheduleEntity;
import lab.cherry.nw.service.ScheduleService;
import lab.cherry.nw.util.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * <pre>
 * ClassName : SchedulelController
 * Type : class
 * Description : 스케줄표 목록 조회, 스케줄표 상세 조회, 스케줄표 컬럼 변환, 스케줄표 날짜 기준 조회 등 스케줄표와 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : ScheduleRepository, ScheduleService, ScheduleServiceImpl
 * </pre>
 */

// MEMO : 재사용성을 위해 ServiceImpl에서만 비즈니스 로직을 사용하기로 함
//        Dto를 통해 알맞는 파라미터로 데이터 가공 후 사용하기로 함
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedule")
@Tag(name = "Schedule", description = "Schedule API Document")
public class ScheduleController {

	private final ScheduleService scheduleService;
    /**
	 +  [ScheduleController] 전체 스케줄표 목록 함수
     *
     * @return 전체 스케줄표  목록을 반환합니다.
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    @GetMapping("")
    @Operation(summary = "스케줄표 목록", description = "스케줄표 목록을 조회합니다.")
    public ResponseEntity<?> findAllSchedule(
            @RequestParam(required = false) String id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) {

        log.info("retrieve all Schedule controller...!");

        Pageable pageable = PageRequest.of(page, size, Sort.by(Common.getOrder(sort)));

        Page<ScheduleEntity> scheduleEntity;
        if(id == null) {
			scheduleEntity = scheduleService.getSchedule(pageable);
        } else {
			scheduleEntity = scheduleService.findPageById(id,pageable);
        }

            return new ResponseEntity<>(scheduleEntity, new HttpHeaders(), HttpStatus.OK);
    }


	/**
	 +  [ScheduleController] 특정 스케줄표 조회 함수
	 *
	 * @return 특정 날짜 스케줄표  목록을 반환합니다.
	 *
	 * Author : hhhaeri(yhoo0020@gmail.com)
	 */
	@GetMapping("{id}")
	@Operation(summary = "ID로 스케줄표 찾기", description = "스케줄표를 조회합니다.")
	public ResponseEntity<?> findByScheduleId(@PathVariable("id") String id) {

		log.info("[ScheduleController] findByScheduleDate...!");

		return new ResponseEntity<>(scheduleService.findById(id), new HttpHeaders(), HttpStatus.OK);
	}

    
  /**
     * [ScheduleController] 스케줄표 생성 함수
     *
     * @param ScheduleCreateDto 생성에 필요한 스케줄표 정보를 담고 있는 객체입니다.
     * @return
     * <pre>
     * true  : 성공(200)을 반환합니다.
     * false : 에러(400)를 반환합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    @PostMapping("")
    @Operation(summary = "스케줄표 생성", description = "스케줄표를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스케줄표 생성이 완료되었습니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "입력 값이 잘못되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> createFinalTemplate(@Valid @RequestBody(required = false) ScheduleEntity.ScheduleCreateDto scheduleCreateDto) {

        log.info("[ScheduleController] createSchedule...!");

        ScheduleEntity scheduleEntity =  scheduleService.createSchedule(scheduleCreateDto);

            return new ResponseEntity<>(scheduleEntity, new HttpHeaders(), HttpStatus.OK);
    }

}
