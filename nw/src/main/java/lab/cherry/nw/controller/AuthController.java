package lab.cherry.nw.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lab.cherry.nw.error.ErrorResponse;
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
 * Type : class
 * Descrption : 사용자 로그인, 회원가입 등 인증과 관련된 함수를 포함하고 있는 클래스입니다.
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입이 완료되었습니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "입력 값이 잘못 되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "중복된 사용자입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "회원가입", description = "사용자를 추가합니다.")
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
    @Operation(summary = "로그인", description = "JWT 인증을 통한 사용자 로그인을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인이 완료되었습니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "입력 값이 잘못 되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "사용자를 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto loginRequest) {

        AccessToken accessToken =  authService.login(loginRequest);

//         Header 에 등록
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", "Bearer " + accessToken.getToken());

        return new ResponseEntity<>(accessToken, new HttpHeaders(), HttpStatus.OK);
    }

}


