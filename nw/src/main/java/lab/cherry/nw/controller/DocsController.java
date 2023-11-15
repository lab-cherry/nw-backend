package lab.cherry.nw.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
import lab.cherry.nw.model.DocsEntity;
import lab.cherry.nw.model.QsheetEntity;
import lab.cherry.nw.service.DocsService;
import lab.cherry.nw.util.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * ClassName : DocsController
 * Type : class
 * Description : 문서 목록 조회, 문서 상세 조회, 문서 업데이트, 문서 삭제, 문서 찾기 등 문서와 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : DocsRepository, DocsService, DocsServiceImpl
 * </pre>
 */

// MEMO : 재사용성을 위해 ServiceImpl에서만 비즈니스 로직을 사용하기로 함
//        Dto를 통해 알맞는 파라미터로 데이터 가공 후 사용하기로 함
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/docs")
@Tag(name = "Docs", description = "Dcos API Document")
public class DocsController {
    private final DocsService docsService;

    /**
     * [DocsController] 전체 문서 목록 함수
     *
     * @return 전체 문서 목록을 반환합니다.
     *
     * Author : taking(taking@duck.com)
     */
    @GetMapping("")
    @Operation(summary = "문서 목록", description = "문서 목록을 조회합니다.")
    public ResponseEntity<?> findAllDocs(
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

    log.info("retrieve all docs controller...!");

    Pageable pageable = PageRequest.of(page, size, Sort.by(Common.getOrder(sort)));
    
    Page<DocsEntity> docsEntity = null;
    docsEntity = docsService.getDocs(pageable);

    // switch(type.toUpperCase()) {
    //     case "ADMIN":
    //         docsEntity = docsService.getDocs(pageable);
    //         break;
    //     // case "USER":
    //     //     return "";
    //     // case "ORG":
    //     //     return "";
    // }

        return new ResponseEntity<>(docsEntity, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [DocsController] 문서 생성 함수
     * <pre>
     * @param docsCreateDto 문서 생성에 필요한 정보를 담고 있는 객체입니다.
     * @return
     * true  : 업데이트된 문서 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @PostMapping(value = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "문서 생성이 완료되었습니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "입력 값이 잘못 되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Operation(summary = "Docs 생성", description = "Docs를 추가합니다.")
    public ResponseEntity<?> createDocs(@Valid @RequestBody(required = false)  DocsEntity.DocsCreateDto docsCreateDto) {
        log.info("[DocsController] createDocs..!");

        ResultResponse result = ResultResponse.of(SuccessCode.DOCS_UPDATE_SUCCESS, docsService.createDocs(docsCreateDto));
        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
    }
    /**
     * [DocsController] 문서 수정 함수
     * <pre>
     * @param id 문서 고유번호를 입력합니다.
     * @param docsUpdateDto 문서 업데이트에 필요한 정보를 담고 있는 객체입니다.
     * @return
     * true  : 업데이트된 문서 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @PatchMapping("{id}")
     @Operation(summary = "문서 업데이트", description = "특정 문서를 업데이트합니다.")
    public ResponseEntity<?> updateById(
            @PathVariable("id") String id,
            @Valid @RequestBody(required = false)  DocsEntity.DocsUpdateDto docsUpdateDto) {
                
        docsService.updateById(id, docsUpdateDto);

        ResultResponse result = ResultResponse.of(SuccessCode.DOCS_UPDATE_SUCCESS, docsService.findById(id));
        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
    }
    /**
     * [DocsController] 특정 문서 삭제 함수
     *
     * @param id 문서 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 문서를 삭제처리합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @DeleteMapping("{id}")
    @Operation(summary = "문서 삭제", description = "문서 삭제합니다.")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {

        log.info("[DocsController] deleteById...!");

        docsService.deleteById(id);

        ResultResponse result = ResultResponse.of(SuccessCode.DOCS_DELETE_SUCCESS, id);
        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
    }


    /**
     * [DocsController] 특정 문서 조회 함수
     *
     * @param id 문서 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 문서 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @GetMapping("{id}")
    @Operation(summary = "ID로 문서 찾기", description = "문서를 조회합니다.")
    public ResponseEntity<?> findByDocsId(@PathVariable("id") String id) {

        log.info("[QsheetController] findByQsheetId...!");

        return new ResponseEntity<>(docsService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }    
}