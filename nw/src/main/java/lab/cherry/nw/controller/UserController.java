package lab.cherry.nw.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lab.cherry.nw.error.enums.SuccessCode;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.service.UserService;
import lab.cherry.nw.error.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * ClassName : UserController
 * Type : class
 * Descrption : 사용자 목록 조회, 사용자 상세 조회, 사용자 업데이트, 사용자 삭제, 사용자 찾기 등 사용자와 관련된 함수를 포함하고 있는 클래스입니다.
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
     * [USER] 전체사용자 목록 함수
     *
     * @author taking(taking@duck.com)
     * @return 전체 사용자 목록을 반환합니다.
     *
     */
    @GetMapping("")
    @Operation(summary = "사용자 목록", description = "사용자 목록을 조회합니다.")
    public ResponseEntity<?> findAllUsers() {
        log.info("retrieve all users controller...!");
//        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.getUsers());
        return new ResponseEntity<>(userService.getUsers(), new HttpHeaders(), HttpStatus.OK);
    }

    // TODO: 업데이트 필요
    /**
     * [USER] 사용자 업데이트 함수
     *
     * @author taking(taking@duck.com)
     * @param userName 사용자 이름을 입력합니다.
     * @param userEmail 사용자 이메일을 입력합니다.
     * @param userEnabled 사용자 활성화 유/무를 입력합니다.
     * @param userPassword 사용자 비밀번호를 입력합니다.
     * @return
     * true  : 업데이트된 사용자 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     */
    @PatchMapping("{id}")
    @Operation(summary = "사용자 업데이트", description = "특정 사용자를 업데이트합니다.")
    public ResponseEntity<?> updateUserById(@PathVariable("id") Integer id,
            @RequestBody UserEntity userDetail) {
//        Map<String, Object> map = new LinkedHashMap<>();

//        UserEntity user = userService.findById(id);

//        try {
//            UserEntity user = userService.findById(id);
////            user.setUsername(userDetail.getUsername());
////            user.setPassword(userDetail.getPassword());
            userService.updateUser(userDetail);
//            map.put("status", 1);
//            map.put("data", userService.findById(id));
//            return new ResponseEntity<>(map, HttpStatus.OK);
//        } catch (Exception ex) {
//            final ErrorResponse response = ErrorResponse.of(ErrorCode.ENTITY_NOT_FOUND);
//            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//        }

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(userService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [USER] 특정 사용자 조회 함수
     *
     * @author taking(taking@duck.com)
     * @param userId 사용자 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 사용자 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     */
    @GetMapping("{id}")
    @Operation(summary = "ID로 사용자 찾기", description = "사용자를 조회합니다.")
    public ResponseEntity<?> findByUserId(@PathVariable("id") Integer id) {
        log.info("[UserController] findByUserId...!");

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.findById(id));
        return new ResponseEntity<>(userService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [USER] 특정 사용자 삭제 함수
     *
     * @author taking(taking@duck.com)
     * @param userId 사용자 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 사용자를 삭제처리합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     */
    @DeleteMapping("{id}")
    @Operation(summary = "사용자 삭제", description = "사용자를 삭제합니다.")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
        log.info("[UserController] deleteUser...!");
        userService.deleteUser(id);

        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

//    @GetMapping("{name}")
//    public ResponseEntity<UserEntity> findByUserName(@PathVariable("name") String name) {
//        log.info("[UserController] findByUserName...!");
//        return new ResponseEntity<>(userService.findByUserName(name), new HttpHeaders(), HttpStatus.OK);
//    }


}
