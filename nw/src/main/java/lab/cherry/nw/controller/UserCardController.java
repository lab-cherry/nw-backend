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
import lab.cherry.nw.model.OrgEntity;
import lab.cherry.nw.model.UserCardEntity;
import lab.cherry.nw.service.OrgService;
import lab.cherry.nw.service.UserCardService;
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
 * ClassName : OrgController
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
@RequestMapping("/api/v1/userCard")
@Tag(name = "Usercard", description = "Usercard API Document")
public class UserCardController {

    private final UserCardService userCardService;

    /**
     * [UserCardController] 전체 조직 목록 함수
     *
     * @return 전체 조직 목록을 반환합니다.
     *
     * Author : taking(taking@duck.com)
     */
    @GetMapping("")
    @Operation(summary = "고객카드 목록", description = "고객카드를 조회합니다.")
    public ResponseEntity<?> findAllUserCards(
            @RequestParam(required = false) String id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        log.info("retrieve all usercard controller...!");

        Pageable pageable = PageRequest.of(page, size, Sort.by(Common.getOrder(sort)));

        Page<UserCardEntity> userCardEntity;
        if(id == null) {
            userCardEntity = userCardService.getUsercards(pageable);
        } else {
            userCardEntity = userCardService.findPageById(id, pageable);
        }

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.getUsers());
        return new ResponseEntity<>(userCardEntity, new HttpHeaders(), HttpStatus.OK);
    }


    /**
     * [UserCardController] 조직 생성 함수
     *
     * @param userCardCreateDto 생성에 필요한 조직 정보를 담고 있는 객체입니다.
     * @return
     * <pre>
     * true  : 성공(200)을 반환합니다.
     * false : 에러(400)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @PostMapping("")
    @Operation(summary = "고객카드 생성", description = "고객카드를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조직 생성이 완료되었습니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "입력 값이 잘못되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> createUserCard(@Valid @RequestBody(required = false) UserCardEntity.CreateDto userCardCreateDto) {

        log.info("[OrgController] createOrganization...!");

        UserCardEntity userCardEntity =  userCardService.createUserCard(userCardCreateDto);

        //         Header 에 등록
        //        HttpHeaders httpHeaders = new HttpHeaders();
        //        httpHeaders.add("Authorization", "Bearer " + accessToken.getToken());

        return new ResponseEntity<>(userCardEntity, new HttpHeaders(), HttpStatus.OK);
    }


    /**
     * [UserCardController] 조직 업데이트 함수
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
    @PatchMapping("{id}")
    @Operation(summary = "고객카드 업데이트", description = "특정 고객카드를 업데이트합니다.")
    public ResponseEntity<?> updateUserCardById(@PathVariable("id") String id, @RequestBody UserCardEntity.UpdateDto userCardEntity) {

        log.info("[OrgController] updateOrgById...!");

        userCardService.updateById(id, userCardEntity);

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(userCardService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [UserCardController] 특정 고객카드 조회 함수
     *
     * @param id 고객카드 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 고객카드 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @GetMapping("{id}")
    @Operation(summary = "ID로 고객카드 찾기", description = "고객카드를 조회합니다.")
    public ResponseEntity<?> findByOrgId(@PathVariable("id") String id) {

        log.info("[USerCardController] findByUserCardId...!");

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.findById(id));
        return new ResponseEntity<>(userCardService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [UserCardController] 특정 조직 삭제 함수
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
    @Operation(summary = "고객카드 삭제", description = "고객카드를 삭제합니다.")
    public ResponseEntity<?> deleteUserCard(@PathVariable("id") String id) {

        log.info("[UserController] deleteUserCard...!");

        userCardService.deleteById(id);

        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
}
