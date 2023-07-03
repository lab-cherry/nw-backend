package lab.cherry.nw.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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



/**
 * <pre>
 * ClassName : Auth Controller
 * Descrption : 사용자 로그인, 회원가입 등 인증과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : AuthService, AuthServiceImpl
 * </pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authenticate", description = "Authenticate API Document")
public class AuthController {

    private final AuthService authService;

    /**
     * [AUTH] 회원가입 함수
     *
     * @author taking(taking@duck.com)
     * @param userName 사용자 이름을 입력합니다.
     * @param userEmail 사용자 이메일을 입력합니다.
     * @param userPassword 사용자 비밀번호를 입력합니다.
     * @return
     * <pre>
     * true  : JWT 인증 토큰을 반환합니다.
     * false : 에러(400, 409)를 반환합니다.
     * </pre>
     */
    @PostMapping("/register")
    @Operation(summary = "회원가입", description = "로그인 처리 API")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDto registerRequest) {

        AccessToken accessToken =  authService.register(registerRequest);

        return new ResponseEntity<>(accessToken, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [AUTH] 로그인 함수
     *
     * @author taking(taking@duck.com)
     * @param userName 사용자 이름을 입력합니다.
     * @param userPassword 사용자 비밀번호를 입력합니다.
     * @return
     * <pre>
     * true  : JWT 인증 토큰을 반환합니다.
     * false : 에러(400, 409)를 반환합니다.
     * </pre>
     */
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "JWT 인증을 통한 로그인 인증")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto loginRequest) {

        AccessToken accessToken =  authService.login(loginRequest);

//         Header 에 등록
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", "Bearer " + accessToken.getToken());

        return new ResponseEntity<>(accessToken, new HttpHeaders(), HttpStatus.OK);
    }

}


