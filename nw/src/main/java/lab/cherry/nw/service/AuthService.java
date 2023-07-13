package lab.cherry.nw.service;

import lab.cherry.nw.model.dto.UserLoginDto;
import lab.cherry.nw.model.dto.UserRegisterDto;
import lab.cherry.nw.util.Security.AccessToken;
import org.springframework.stereotype.Component;


/**
 * <pre>
 * ClassName : AuthService
 * Type : interface
 * Descrption : Auth와 관련된 함수를 정리한 인터페이스입니다.
 * Related : AuthController, AuthServiceImpl
 * </pre>
 */
@Component
public interface AuthService {
    AccessToken register(UserRegisterDto userRegisterDto);
    AccessToken login(UserLoginDto userLoginDto);
}
