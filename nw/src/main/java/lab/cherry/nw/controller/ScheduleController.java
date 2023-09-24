package lab.cherry.nw.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lab.cherry.nw.error.ErrorResponse;
import lab.cherry.nw.error.ResultResponse;
import lab.cherry.nw.error.enums.SuccessCode;
import lab.cherry.nw.model.FinalTemplEntity;
import lab.cherry.nw.model.ScheduleEntity;
import lab.cherry.nw.service.FinalTemplService;
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
 * Description : 스케줄표 목록 조회, 스케줄표 상세 조회, 스케줄표 업데이트, 스케줄표 삭제, 스케줄표 찾기 등 스케줄표와 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : ScheduleRepository, ScheduleService, ScheduleServiceImpl
 * </pre>
 */

// MEMO : 재사용성을 위해 ServiceImpl에서만 비즈니스 로직을 사용하기로 함
//        Dto를 통해 알맞는 파라미터로 데이터 가공 후 사용하기로 함
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/nw/api/v1/schedule")
@Tag(name = "Schedule", description = "Schedule API Document")
public class ScheduleController {

    private final FinalTemplService finalTemplService;
	private final ScheduleService scheduleService;
    /**
     * [SchedulelController] 전체 스케줄표 템플릿 목록 함수
     *
     * @return 전체 스케줄표  목록을 반환합니다.
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    @GetMapping("")
    @Operation(summary = "스케줄표 목록", description = "스케줄표 목록을 조회합니다.")
    public ResponseEntity<?> findAllFinalTemplate(
            @RequestParam(required = false) String id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) {

        log.info("retrieve all FinalTempl controller...!");

        Pageable pageable = PageRequest.of(page, size, Sort.by(Common.getOrder(sort)));

        Page<FinalTemplEntity> finalTemplateEntity;
        if(id == null) {
            finalTemplateEntity = finalTemplService.getFinalTemplate(pageable);
        } else {
            finalTemplateEntity = finalTemplService.findPageById(id,pageable);
        }

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.getUsers());
            return new ResponseEntity<>(finalTemplateEntity, new HttpHeaders(), HttpStatus.OK);
    }


    /**
     * [SchedulelController] 스케줄표 컬럼 변경 함수
     *
     * @param finalTemplCreateDto 생성에 필요한 조직 정보를 담고 있는 객체입니다.
     * @return
     * <pre>
     * true  : 성공(200)을 반환합니다.
     * false : 에러(400)를 반환합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    @PostMapping("")
    @Operation(summary = "스케줄표 컬럼 변환", description = "최종확인서에 있는 컬럼을 최종확인서로 변환.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조직 최종확인서 템플릿 생성이 완료되었습니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "입력 값이 잘못되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> fianlToSchedule(@Valid @RequestBody(required = false) ScheduleEntity.transDto scheduleTransDto) {

        log.info("[SchedulelController] fianlToSchedule...!");

		ScheduleEntity scheduleEntity = scheduleService.transColumn(scheduleTransDto);


            return new ResponseEntity<>(scheduleEntity, new HttpHeaders(), HttpStatus.OK);
    }


    /**
     * [FinalTemplController] 스케줄표 업데이트 함수
     *
     * @param id 스케줄표 고유번호를 입력합니다.
     * @param FinalTemplEntity 스케줄표 업데이트에 필요한 정보를 담고 있는 객체입니다.
     * @return
     * <pre>
     * true  : 업데이트된 스케줄표 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    @PatchMapping("{id}")
    @Operation(summary = "최종 확인서 템플릿 업데이트", description = "특정 최종 확인서 템플릿을 업데이트합니다.")
    public ResponseEntity<?> updateFinalTemplById(@PathVariable("id") String id, @RequestBody FinalTemplEntity.UpdateDto finaltemplEntity) {

        log.info("[FinalTemplController] updateFinaltemplById...!");

        finalTemplService.updateById(id, finaltemplEntity);

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(finalTemplService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [FinalTemplController] 특정 스케줄표 조회 함수
     *
     * @param id 조직 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 조직 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @GetMapping("{id}")
    @Operation(summary = "ID로 스케줄표 찾기", description = "스케줄표을 조회합니다.")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {

        log.info("[FinaldocsController] findByFinalTemplId...!");

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.findById(id));
            return new ResponseEntity<>(finalTemplService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [FinalTemplController] 특정 스케줄표 삭제 함수
     *
     * @param id 조직 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 조직 삭제처리합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @DeleteMapping("{id}")
    @Operation(summary = "최종 확인서 템플릿 삭제", description = "최종 확인서 템플릿을 삭제합니다.")
    public ResponseEntity<?> deleteFinalTemplate(@PathVariable("id") String id) {

        log.info("[FinaldocsController] deleteFinalTempl...!");

        finalTemplService.deleteById(id);

        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
}
