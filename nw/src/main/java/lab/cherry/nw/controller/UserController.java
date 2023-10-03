package lab.cherry.nw.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lab.cherry.nw.error.ResultResponse;
import lab.cherry.nw.error.enums.SuccessCode;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.service.UserService;
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
 * ClassName : UserController
 * Type : class
 * Description : 사용자 목록 조회, 사용자 상세 조회, 사용자 업데이트, 사용자 삭제, 사용자 찾기 등 사용자와 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : UserRepository, UserService, UserServiceImpl
 * </pre>
 */

// MEMO : 재사용성을 위해 ServiceImpl에서만 비즈니스 로직을 사용하기로 함
//        Dto를 통해 알맞는 파라미터로 데이터 가공 후 사용하기로 함
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "User", description = "User API Document")
public class UserController {

    private final UserService userService;

    /**
     * [UserController] 전체사용자 목록 함수
     *
     * @return 전체 사용자 목록을 반환합니다.
     *
     * Author : taking(taking@duck.com)
     */
    @GetMapping("")
    @Operation(summary = "사용자 목록", description = "사용자 목록을 조회합니다.")
    public ResponseEntity<?> findAllUsers(
            @RequestParam(required = false) String userid,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        log.info("retrieve all users controller...!");

        Pageable pageable = PageRequest.of(page, size, Sort.by(Common.getOrder(sort)));

        Page<UserEntity> userEntity;
        if(userid == null) {
            userEntity = userService.getUsers(pageable);
        } else {
            userEntity = userService.findPageByUserId(userid, pageable);
        }

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.getUsers());
        return new ResponseEntity<>(userEntity, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [UserController] 사용자 업데이트 함수
     *
     * @param id 사용자 고유번호를 입력합니다.
     * @param userEntity 사용자 업데이트에 필요한 사용자 정보를 담고 있는 객체입니다.
     * @return
     * <pre>
     * true  : 업데이트된 사용자 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @PatchMapping("{id}")
    @Operation(summary = "사용자 업데이트", description = "특정 사용자를 업데이트합니다.")
    public ResponseEntity<?> updateUser(
            @PathVariable("id") String id,
            @RequestBody UserEntity.UserUpdateDto userEntity) {

        log.info("[UserController] updateUser...!");

        userService.updateById(id, userEntity);

        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }


    /**
     * [UserController] 사용자 조직 업데이트 함수
     *
     * @param id 사용자 고유번호를 입력합니다.
     * @param userEntity 사용자의 조직을 업데이트하기 위한 조직 고유아이디를 갖고 있는 객체입니다.업데이트에 필요한 사용자 정보를 담고 있는 객체입니다.
     * @return
     * <pre>
     * true  : 업데이트된 사용자 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @PatchMapping("{id}/org")
    @Operation(summary = "사용자 조직 업데이트", description = "특정 사용자의 조직 정보를 업데이트합니다.")
    public ResponseEntity<?> updateUserOrgs(
		@PathVariable("id") String id,
		@RequestBody UserEntity.UserUpdateDto userEntity) {

            log.info("[UserController] updateUserOrg...!");

			userService.updateOrgById(id, userEntity.getOrgId());

            final ResultResponse response = ResultResponse.of(SuccessCode.OK);
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }


    /**
     * [UserController] 특정 사용자 조회 함수
     *
     * @param id 사용자 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 사용자 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * How-to
     *  1. /api/v1/user?id={user_tsid}
     *  2. /api/v1/user?userId={user_id}
     *
     * Author : taking(taking@duck.com)
     */
    @GetMapping("{id}")
    @Operation(summary = "사용자 찾기", description = "사용자를 조회합니다.")
    public ResponseEntity<?> findUser(
            @PathVariable("id") String id) {

        log.info("[UserController] findUser...!");

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.findById(id));
        return new ResponseEntity<>(userService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [UserController] 특정 사용자 삭제 함수
     *
     * @param id 사용자 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 사용자를 삭제처리합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @DeleteMapping("{id}")
    @Operation(summary = "사용자 삭제", description = "사용자를 삭제합니다.")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {

        log.info("[UserController] deleteUser...!");

        userService.deleteById(id);

        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
}
