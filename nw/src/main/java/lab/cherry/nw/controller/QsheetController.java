package lab.cherry.nw.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lab.cherry.nw.error.enums.SuccessCode;
import lab.cherry.nw.model.QsheetEntity;
import lab.cherry.nw.service.QsheetService;
import lab.cherry.nw.error.ResultResponse;
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
import java.util.List;
import java.util.Map;

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

    Page<QsheetEntity> userEntity;
    if(userid == null) {
        userEntity = qsheetService.getUsers(pageable);
    } else {
//        userEntity = qsheetService.findPageByUserId(userid, pageable);
    }
        return new ResponseEntity<>(qsheetService, new HttpHeaders(), HttpStatus.OK);
    }
//
//    // TODO: 업데이트 필요
//    /**
//     * [QsheetController] 큐시트 업데이트 함수
//     *
//     * @param id 큐시트 고유번호를 입력합니다.
//     * @param qsheetDetail 큐시트 업데이트에 필요한 정보를 담고 있는 객체입니다.
//     * @return
//     * <pre>
//     * true  : 업데이트된 큐시트 정보를 반환합니다.
//     * false : 에러(400, 404)를 반환합니다.
//     * </pre>
//     *
//     * Author : yby654(yby654@github.com)
//     */
//    @PatchMapping("{id}")
//    @Operation(summary = "큐시트 업데이트", description = "특정 큐시트을 업데이트합니다.")
//    public ResponseEntity<?> updateQsheetById(@PathVariable("id") Long id,
//            @RequestBody QsheetEntity qsheetDetail) {
////        Map<String, Object> map = new LinkedHashMap<>();
//
////        UserEntity user = userService.findById(id);
//
////        try {
////            UserEntity user = userService.findById(id);
//////            user.setUsername(userDetail.getUsername());
//////            user.setPassword(userDetail.getPassword());
//        qsheetService.updateQsheet(qsheetDetail);
////            map.put("status", 1);
////            map.put("data", userService.findById(id));
////            return new ResponseEntity<>(map, HttpStatus.OK);
////        } catch (Exception ex) {
////            final ErrorResponse response = ErrorResponse.of(ErrorCode.ENTITY_NOT_FOUND);
////            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
////        }
//
////        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
//        return new ResponseEntity<>(qsheetService.findById(id), new HttpHeaders(), HttpStatus.OK);
//    }
//
//    /**
//     * [QsheetController] 특정 큐시트 조회 함수
//     *
//     * @param id 큐시트 고유번호를 입력합니다.
//     * @return
//     * <pre>
//     * true  : 특정 큐시트 정보를 반환합니다.
//     * false : 에러(400, 404)를 반환합니다.
//     * </pre>
//     *
//     * Author : yby654(yby654@github.com)
//     */
//    @GetMapping("{id}")
//    @Operation(summary = "ID로 큐시트 찾기", description = "큐시트을 조회합니다.")
//    public ResponseEntity<?> findByQsheetId(@PathVariable("id") Long id) {
//        log.info("[QsheetController] findByQsheetId...!");
//
////        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.findById(id));
//        return new ResponseEntity<>(qsheetService.findById(id), new HttpHeaders(), HttpStatus.OK);
//    }
//
//    /**
//     * [QsheetController] 특정 큐시트 삭제 함수
//     *
//     * @param id 큐시트 고유번호를 입력합니다.
//     * @return
//     * <pre>
//     * true  : 특정 큐시트 삭제처리합니다.
//     * false : 에러(400, 404)를 반환합니다.
//     * </pre>
//     *
//     * Author : yby654(yby654@github.com)
//     */
//    @DeleteMapping("{id}")
//    @Operation(summary = "큐시트 삭제", description = "큐시트을 삭제합니다.")
//    public ResponseEntity<?> deleteQsheet(@PathVariable("id") Long id) {
//        log.info("[UserController] deleteUser...!");
//        qsheetService.deleteQsheet(id);
//
//        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
//        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
//    }
}
