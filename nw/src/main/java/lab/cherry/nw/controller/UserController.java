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

    @GetMapping("")
    public ResponseEntity<?> findAllUsers() {
        log.info("retrieve all users controller...!");
        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.getUsers());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

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

        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findByUserId(@PathVariable("id") Long id) {
        log.info("[UserController] findByUserId...!");

        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.findById(id));
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        log.info("[UserController] deleteUser...!");
        userService.deleteUser(id);

        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

//    @GetMapping("/find/name/{name}")
//    public ResponseEntity<UserEntity> findByName(@PathVariable("name") String name) {
//        logger.info("[UserController] findByUserName...!");
//        UserEntity userEntity = userService.findByUserName(name);
//        return new ResponseEntity<>(userEntity, new HttpHeaders(), HttpStatus.OK);
//    }


}
