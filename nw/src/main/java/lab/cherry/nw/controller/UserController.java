package lab.cherry.nw.controller;

import lab.cherry.nw.error.ErrorResponse;
import lab.cherry.nw.error.enums.ErrorCode;
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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
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
    public ResponseEntity<?> findAllUsers() {
        log.info("retrieve all users controller...!");
//        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.getUsers());
        return new ResponseEntity<>(userService.getUsers(), new HttpHeaders(), HttpStatus.OK);
    }

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
    public ResponseEntity<?> updateUserById(@PathVariable("id") Long id,
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
    public ResponseEntity<?> findByUserId(@PathVariable("id") Long id) {
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
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
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
