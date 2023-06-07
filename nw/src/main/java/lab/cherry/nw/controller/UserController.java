package lab.cherry.nw.controller;

import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<?> findAllUsers() {
        log.info("retrieve all users controller...!");
        return new ResponseEntity<>(userService.getUsers(), new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUserById(@PathVariable("id") Long id,
            @RequestBody UserEntity userDetail) {
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            UserEntity user = userService.findById(id);
//            user.setUsername(userDetail.getUsername());
//            user.setPassword(userDetail.getPassword());
//            userService.createUser(user);
            map.put("status", 1);
            map.put("data", userService.findById(id));
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception ex) {
            map.clear();
            map.put("status", 0);
            map.put("message", "User is not found");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findByUserId(@PathVariable("id") Long id) {
        log.info("[UserController] findByUserId...!");
        UserEntity userEntity = userService.findById(id);
        return new ResponseEntity<>(userEntity, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        log.info("[UserController] deleteUser...!");
        userService.deleteUser(id);
        return new ResponseEntity<>("User Delete Success...!", new HttpHeaders(), HttpStatus.OK);
    }

//    @GetMapping("/find/name/{name}")
//    public ResponseEntity<UserEntity> findByName(@PathVariable("name") String name) {
//        logger.info("[UserController] findByUserName...!");
//        UserEntity userEntity = userService.findByUserName(name);
//        return new ResponseEntity<>(userEntity, new HttpHeaders(), HttpStatus.OK);
//    }


}
