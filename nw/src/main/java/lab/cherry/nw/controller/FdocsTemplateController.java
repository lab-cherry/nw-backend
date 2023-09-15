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
import lab.cherry.nw.model.FdocsTemplateEntity;
import lab.cherry.nw.model.FinaldocsEntity;
import lab.cherry.nw.service.FdocsTemplateService;
import lab.cherry.nw.service.FinaldocsService;
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
 * ClassName : FinaldocsController
 * Type : class
 * Description : 조직 목록 조회, 조직 상세 조회, 조직 업데이트, 조직 삭제, 조직 찾기 등 조직과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : OrgRepository, OrgService, OrgServiceImpl
 * </pre>
 */

// MEMO : 재사용성을 위해 ServiceImpl에서만 비즈니스 로직을 사용하기로 함
//        Dto를 통해 알맞는 파라미터로 데이터 가공 후 사용하기로 함
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fdocsTemp")
@Tag(name = "Finaldocs", description = "Finaldocs API Document")
public class FdocsTemplateController {

    private final FdocsTemplateService fdocsTemplateService;
    /**
     * [FinaldocsController] 전체 조직 목록 함수
     *
     * @return 전체 조직 목록을 반환합니다.
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    @GetMapping("")
    @Operation(summary = "최종 확인서 템플릿 목록", description = "최종 확인서 템플릿목록을 조회합니다.")
    public ResponseEntity<?> findAllFdocsTemplate(
            @RequestParam(required = false) String id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) {

        log.info("retrieve all finaldocs controller...!");

        Pageable pageable = PageRequest.of(page, size, Sort.by(Common.getOrder(sort)));

        Page<FdocsTemplateEntity> fdocsTemplateEntity;
        if(id == null) {
            fdocsTemplateEntity = fdocsTemplateService.getFdocsTemplate(pageable);
        } else {
            fdocsTemplateEntity = fdocsTemplateService.findPageById(id,pageable);
        }

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.getUsers());
            return new ResponseEntity<>(fdocsTemplateEntity, new HttpHeaders(), HttpStatus.OK);
    }


    /**
     * [OrgController] 조직 생성 함수
     *
     * @param finaldocsCreateDto 생성에 필요한 조직 정보를 담고 있는 객체입니다.
     * @return
     * <pre>
     * true  : 성공(200)을 반환합니다.
     * false : 에러(400)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @PostMapping("")
    @Operation(summary = "최종확인서 생성", description = "최종확인서를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조직 최종확인서 생성이 완료되었습니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "입력 값이 잘못되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> createFdocsTemplate(@Valid @RequestBody(required = false) FdocsTemplateEntity.CreateDto fdocsTemplateCreateDto) {

        log.info("[FinaldocksController] createFinaldocs...!");



        FdocsTemplateEntity finaldocsEntity =  fdocsTemplateService.createFdocsTemplate(fdocsTemplateCreateDto);

        //         Header 에 등록
        //        HttpHeaders httpHeaders = new HttpHeaders();
        //        httpHeaders.add("Authorization", "Bearer " + accessToken.getToken());

            return new ResponseEntity<>(finaldocsEntity, new HttpHeaders(), HttpStatus.OK);
    }


    /**
     * [OrgController] 조직 업데이트 함수
     *
     * @param id 조직 고유번호를 입력합니다.
     * @param orgEntity 조직 업데이트에 필요한 정보를 담고 있는 객체입니다.
     * @return
     * <pre>
     * true  : 업데이트된 조직 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
//    @PatchMapping("{id}")
//    @Operation(summary = "최종 확인서 업데이트", description = "특정 최종 확인서를 업데이트합니다.")
//    public ResponseEntity<?> updateOrgById(@PathVariable("id") String id, @RequestBody OrgEntity.UpdateDto orgEntity) {
//
//        log.info("[OrgController] updateOrgById...!");
//
//        orgService.updateById(id, orgEntity);
//
////        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
//        return new ResponseEntity<>(orgService.findById(id), new HttpHeaders(), HttpStatus.OK);
//    }

    /**
     * [OrgController] 특정 조직 조회 함수
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
    @Operation(summary = "ID로 최종확인서 찾기", description = "최종확인서를 조회합니다.")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {

        log.info("[FinaldocsController] findByFinaldocsId...!");

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.findById(id));
            return new ResponseEntity<>(fdocsTemplateService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [OrgController] 특정 조직 삭제 함수
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
    @Operation(summary = "최종 확인서 삭제", description = "최종 확인서를 삭제합니다.")
    public ResponseEntity<?> deleteFdocsTemplate(@PathVariable("id") String id) {

        log.info("[FinaldocsController] deleteFinaldocs...!");

        fdocsTemplateService.deleteById(id);

        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
}
