package lab.cherry.nw.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lab.cherry.nw.error.ErrorResponse;
import lab.cherry.nw.error.ResultResponse;
import lab.cherry.nw.error.enums.SuccessCode;
import lab.cherry.nw.model.QsheetEntity;
import lab.cherry.nw.service.QsheetService;
import lab.cherry.nw.util.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * ClassName : QsheetController
 * Type : class
 * Description : 큐시트 목록 조회, 큐시트 상세 조회, 큐시트 업데이트, 큐시트 삭제, 큐시트 찾기 등 큐시트과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : QsheetRepository, QsheetService, QsheetServiceImpl
 * </pre>
 */

// MEMO : 재사용성을 위해 ServiceImpl에서만 비즈니스 로직을 사용하기로 함
//        Dto를 통해 알맞는 파라미터로 데이터 가공 후 사용하기로 함
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/qsheet")
@Tag(name = "Qsheet", description = "Qsheet API Document")
public class QsheetController {
    private final QsheetService qsheetService;

    /**
     * [QsheetController] 전체 큐시트 목록 함수
     *
     * @return 전체 큐시트 목록을 반환합니다.
     *
     * Author : yby654(yby654@github.com)
     */
    @GetMapping("")
    @Operation(summary = "큐시트 목록", description = "큐시트 목록을 조회합니다.")
    public ResponseEntity<?> findAllQsheets(
            @RequestParam(required = false) String userid,
            @RequestParam(required = false) String orgid,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

    log.info("retrieve all qsheets controller...!");

    Pageable pageable = PageRequest.of(page, size, Sort.by(Common.getOrder(sort)));

    Page<QsheetEntity> qsheetEntity;
    if(userid == null && orgid==null) {
        qsheetEntity = qsheetService.getQsheets(pageable);
    } else if(userid != null && orgid==null) {
        qsheetEntity = qsheetService.findPageByUserId(userid, pageable);
	} else{
		qsheetEntity = qsheetService.findPageByOrgId(orgid, pageable);
	}
        for (QsheetEntity qsheet : qsheetEntity) {
            qsheet.sortDataByOrderIndex();
        }
        return new ResponseEntity<>(qsheetEntity, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [QsheetController] 큐시트 생성 함수
     * <pre>
     * @param qsheetCreateDto 큐시트 생성에 필요한 큐시트 정보를 담고 있는 객체입니다.
     * @return
     * true  : 업데이트된 큐시트 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "큐시트 생성이 완료되었습니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "입력 값이 잘못 되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Operation(summary = "Qsheet 생성", description = "Qsheet를 추가합니다.")
    public ResponseEntity<?> createQsheet(@RequestPart QsheetEntity.QsheetCreateDto qsheetCreateDto, @RequestPart(required = false) List<MultipartFile> files) {
        log.info("[QsheetController] createQsheet...!");
        // log.error ("files : {}", files);
        // for(MultipartFile file:files){
        //     if(file.isEmpty()){
        //         files = null;
        //         break;
        //     }
        // }
        
        qsheetService.createQsheet(qsheetCreateDto, files);

        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
    /**
     * [QsheetController] 큐시트 수정 함수
     * <pre>
     * @param id 큐시트 고유번호를 입력합니다.
     * @param qsheetUpdateDto 큐시트 업데이트에 필요한 큐시트 정보를 담고 있는 객체입니다.
     * @return
     * true  : 업데이트된 큐시 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @PatchMapping("{id}")
     @Operation(summary = "큐시트 업데이트", description = "특정 큐시트를 업데이트합니다.")
    public ResponseEntity<?> updateById(
            @PathVariable("id") String id,
            @RequestPart QsheetEntity.QsheetUpdateDto qsheetUpdateDto, @RequestPart(required = false) List<MultipartFile> files) {
        
                // log.info("files : {} ", files);
        log.info("[QsheetController] updateQsheet...!");
        // for(MultipartFile file:files){
        //     if(file.isEmpty()){
        //         files = null;
        //         break;
        //     }
        // }
        qsheetService.updateById(id, qsheetUpdateDto, files);
//        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(qsheetService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }
    /**
     * [QsheetController] 특정 큐시트 삭제 함수
     *
     * @param id 큐시트 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 큐시트를 삭제처리합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @DeleteMapping("{id}")
    @Operation(summary = "큐시트 삭제", description = "큐시트를 삭제합니다.")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {

        log.info("[UserController] deleteUser...!");

        qsheetService.deleteById(id);

        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [QsheetController] 특정 역할 조회 함수
     *
     * @param id 큐시트 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 큐시트 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @GetMapping("{id}")
    @Operation(summary = "ID로 큐시트 찾기", description = "큐시트를 조회합니다.")
    public ResponseEntity<?> findByQsheetId(@PathVariable("id") String id) {

        log.info("[QsheetController] findByQsheetId...!");

        //        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.findById(id));
        return new ResponseEntity<>(qsheetService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }
    
	/**
     * [QsheetController] 큐시트 사용자 파일 다운로드 함수
     *
     * @return 큐시트 사용자 파일을 반환합니다.
     *
     * Author : taking(taking@duck.com)
     */
    @PostMapping("/download")
    @Operation(summary = "큐시트 사용자 파일 다운로드", description = "큐시트 사용자 파일을 다운로드합니다.")
    public ResponseEntity<?> downloadQsheetBySeq(@RequestBody QsheetEntity.QsheetDownloadDto qsheetDownloadDto) {

        log.info("[QsheetController] downloadQsheetBySeq...!");


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "download.zip");

        return new ResponseEntity<>(qsheetService.download(qsheetDownloadDto.getUser()), new HttpHeaders(), HttpStatus.OK);

   }

}
