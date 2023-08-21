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
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.service.AuthService;
import lab.cherry.nw.util.Security.AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * ClassName : Auth Controller
 * Type : class
 * Description : 사용자 로그인, 회원가입 등 인증과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : AuthService, AuthServiceImpl
 * </pre>
 */

// MEMO : 재사용성을 위해 ServiceImpl에서만 비즈니스 로직을 사용하기로 함
//        Dto를 통해 알맞는 파라미터로 데이터 가공 후 사용하기로 함
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authenticate", description = "Authenticate API Document")
public class AuthController {

    private final AuthService authService;

    /**
     * [AuthController] 회원가입 함수
     *
     * @param userRegisterDto 회원가입에 필요한 사용자 정보를 담고 있는 객체입니다.
     * @return
     * <pre>
     * true  : JWT 인증 토큰을 반환합니다.
     * false : 에러(400, 409)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입이 완료되었습니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "입력 값이 잘못 되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "중복된 사용자입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "회원가입", description = "사용자를 추가합니다.")
    public ResponseEntity<?> register(@Valid @RequestBody UserEntity.RegisterDto userRegisterDto) {

        AccessToken accessToken =  authService.register(userRegisterDto);
        return new ResponseEntity<>(accessToken, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [AuthController] 로그인 함수
     *
     * @param userLoginDto 회원가입에 필요한 사용자 정보를 담고 있는 객체입니다.
     * @return
     * <pre>
     * true  : JWT 인증 토큰을 반환합니다.
     * false : 에러(400, 409)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "JWT 인증을 통한 사용자 로그인을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인이 완료되었습니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "입력 값이 잘못 되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "사용자를 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> login(@Valid @RequestBody(required = false) UserEntity.LoginDto userLoginDto) {

        AccessToken accessToken =  authService.login(userLoginDto);

//         Header 에 등록
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", "Bearer " + accessToken.getToken());

        return new ResponseEntity<>(accessToken, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [AuthController] 사용자 이름 중복 체크 함수
     *
     * @param username 사용자 이름을 입력합니다.
     * @return boolean
     * <pre>
     * true  : 특정 사용자를 삭제처리합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @GetMapping("/check/{username}")
    @Operation(summary = "사용자 아이디 중복체크", description = "사용자 아이디를 중복체크합니다.")
    public ResponseEntity<?> existUsername(@PathVariable("username") String username) {
        log.info("[UserController] existUsername...!");

        authService.checkExistsWithUserName(username);

        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
}