package lab.cherry.nw.controller;

import lab.cherry.nw.model.dto.UserLoginDto;
import lab.cherry.nw.model.dto.UserRegisterDto;
import lab.cherry.nw.service.AuthService;
import lab.cherry.nw.util.Security.AccessToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDto registerRequest) {

        AccessToken accessToken =  authService.register(registerRequest);

        return new ResponseEntity<>(accessToken, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto loginRequest) {

        AccessToken accessToken =  authService.login(loginRequest);

//        Map<String, Object> map = new LinkedHashMap<>();
//        map.put("message", "Success Created.");
//        map.put("code", "200");
//        map.put("username", loginRequest.getUsername());
//        map.put("token", accessToken.getToken());


        // Header 에 등록
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", "Bearer " + accessToken.getToken());

        return new ResponseEntity<>(accessToken, new HttpHeaders(), HttpStatus.OK);

    }

}


