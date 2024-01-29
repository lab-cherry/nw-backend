package lab.cherry.nw.controller;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lab.cherry.nw.model.QsheetHistoryEntity;
import lab.cherry.nw.service.QsheetHistoryService;
import lab.cherry.nw.util.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * <pre>
 * ClassName : QsheetHistoryController
 * Type : class
 * Description : 큐시트 히스토리 목록 조회, 큐시트 히스토리 상세 조회, 큐시트 히스토리 업데이트, 큐시트 히스토리 삭제, 큐시트 히스토리 찾기 등 큐시트 히스토리과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : QsheetHistoryRepository, QsheetHistoryService, QsheetHistoryServiceImpl
 * </pre>
 */

// MEMO : 재사용성을 위해 ServiceImpl에서만 비즈니스 로직을 사용하기로 함
//        Dto를 통해 알맞는 파라미터로 데이터 가공 후 사용하기로 함
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/qsheetHistory")
@Tag(name = "QsheetHistory", description = "QsheetHistory API Document")
public class QsheetHistoryController {
    private final QsheetHistoryService qsheetHistoryService;
 
    /**
     * [QsheetHistoryController] 전체 큐시트 히스토리 목록 함수
     *
     * @return 전체 큐시트 히스토리 목록을 반환합니다.
     *
     * Author : yby654(yby654@github.com)
     */
    @GetMapping("")
    @Operation(summary = "큐시트 히스토리 목록", description = "큐시트 히스토리 목록을 조회합니다.")
    public ResponseEntity<?> findAllQsheetHistory(
            @RequestParam(required = false) String userid,
            @RequestParam(required = false) String qsheetid,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

    log.info("retrieve all QsheetHistroy controller...!");

    Pageable pageable = PageRequest.of(page, size, Sort.by(Common.getOrder(sort)));

    Page<QsheetHistoryEntity> qsheetHistoryEntity;
    if(userid == null && qsheetid==null) {
        qsheetHistoryEntity = qsheetHistoryService.getQsheetHistorys(pageable);
    } else if(userid != null && qsheetid==null) {
        qsheetHistoryEntity = qsheetHistoryService.findPageByUserId(userid, pageable);
    }else {
        qsheetHistoryEntity = qsheetHistoryService.findPageByQsheetId(qsheetid, pageable);
    }
        return new ResponseEntity<>(qsheetHistoryEntity, new HttpHeaders(), HttpStatus.OK);
	}

    /**
     * [QsheetHistoryController] 특정 큐시트 히스토리 조회 함수
     *
     * @param qsheetId 큐시트 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 큐시트 히스토리 목록을 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @GetMapping("/qsheet/{id}")
    @Operation(summary = "Qsheet ID로 히스토리 찾기", description = "큐시트 히스토리를 조회합니다.")
    public ResponseEntity<?> find(@PathVariable("id") String qsheetId) {
        log.info("[QsheetController] findByQsheetId...!");

        //        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.findById(id));
        return new ResponseEntity<>(qsheetHistoryService.findByQsheetId(qsheetId), new HttpHeaders(), HttpStatus.OK);
    }


}
