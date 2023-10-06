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
import lab.cherry.nw.model.OrgEntity;
import lab.cherry.nw.service.FinalTemplService;
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
 * ClassName : FinalTemplController
 * Type : class
 * Description : 최종확인서 템플릿 목록 조회, 최종확인서 템플릿 상세 조회, 최종확인서 템플릿 업데이트, 최종확인서 템플릿 삭제, 최종확인서 템플릿 찾기 등 최종확인서 템플릿과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : FianlTemplRepository, FianlTemplService, FianlTemplServiceImpl
 * </pre>
 */

// MEMO : 재사용성을 위해 ServiceImpl에서만 비즈니스 로직을 사용하기로 함
//        Dto를 통해 알맞는 파라미터로 데이터 가공 후 사용하기로 함
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/nw/api/v1/finaltempl")
@Tag(name = "Finaltempl", description = "Finaltempl API Document")
public class FinalTemplController {

    private final FinalTemplService finalTemplService;
    /**
     * [FinalTemplController] 전체 최종확인서 템플릿 목록 함수
     *
     * @return 전체 최종확인서 템플릿  목록을 반환합니다.
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    @GetMapping("")
    @Operation(summary = "최종 확인서 템플릿 목록", description = "최종 확인서 템플릿 목록을 조회합니다.")
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

            return new ResponseEntity<>(finalTemplateEntity, new HttpHeaders(), HttpStatus.OK);
    }


    /**
     * [FinalTemplController] 최종확인서 템플릿 생성 함수
     *
     * @param finalTemplateCreateDto 생성에 필요한 최종확인서 템플릿 정보를 담고 있는 객체입니다.
     * @return
     * <pre>
     * true  : 성공(200)을 반환합니다.
     * false : 에러(400)를 반환합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    @PostMapping("")
    @Operation(summary = "최종확인서 템플릿 생성", description = "최종확인서 템플릿을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "최종확인서 템플릿 생성이 완료되었습니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "입력 값이 잘못되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> createFinalTemplate(@Valid @RequestBody(required = false) FinalTemplEntity.FinalTemplCreateDto finalTemplateCreateDto) {

        log.info("[FinalTemplController] createFinalTempl...!");

        FinalTemplEntity finalTemplEntity =  finalTemplService.createFinalTemplate(finalTemplateCreateDto);

            return new ResponseEntity<>(finalTemplEntity, new HttpHeaders(), HttpStatus.OK);
    }


    /**
     * [FinalTemplController] 최종확인서 템플릿 업데이트 함수
     *
     * @param id 최종확인서 템플릿 고유번호를 입력합니다.
     * @param finaltemplEntity 최종확인서 템플릿 업데이트에 필요한 정보를 담고 있는 객체입니다.
     * @return
     * <pre>
     * true  : 업데이트된 최종확인서 템플릿 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    @PatchMapping("{id}")
    @Operation(summary = "최종 확인서 템플릿 업데이트", description = "특정 최종 확인서 템플릿을 업데이트합니다.")
    public ResponseEntity<?> updateFinalTemplById(@PathVariable("id") String id, @RequestBody FinalTemplEntity.FinalTemplUpdateDto finaltemplEntity) {

        log.info("[FinalTemplController] updateFinaltemplById...!");

        finalTemplService.updateById(id, finaltemplEntity);

        return new ResponseEntity<>(finalTemplService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [FinalTemplController] 특정 최종확인서 템플릿 조회 함수
     *
     * @param id 최종확인서 템플릿 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 최종확인서 템플릿 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
	 * Author : hhhaeri(yhoo0020@gmail.com)
     */
    @GetMapping("{id}")
    @Operation(summary = "ID로 최종확인서 템플릿 찾기", description = "최종확인서 템플릿을 조회합니다.")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {

        log.info("[FinaldocsController] findByFinalTemplId...!");

            return new ResponseEntity<>(finalTemplService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [FinalTemplController] 특정 최종확인서 템플릿 삭제 함수
     *
     * @param id 최종확인서 템플릿 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 최종확인서 템플릿 삭제처리합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
	 * Author : hhhaeri(yhoo0020@gmail.com)
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
