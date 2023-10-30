package lab.cherry.nw.service;

import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.util.Security.AccessToken;
import java.util.List;
import org.springframework.stereotype.Component;


/**
 * <pre>
 * ClassName : AuthService
 * Type : interface
 * Description : Auth와 관련된 함수를 정리한 인터페이스입니다.
 * Related : AuthController, AuthServiceImpl
 * </pre>
 */
@Component
public interface AuthService {
    UserEntity register(UserEntity.UserRegisterDto userRegisterDto);
    List<UserEntity> addOrgUser(List<UserEntity.UserRegisterDto> userRegisterDtoList);
    AccessToken.Get login(UserEntity.UserLoginDto userLoginDto);
    void checkExistsWithUserId(String userid);
	UserEntity myInfo();
    void confirmEmail(String email, String token);
    void reConfirmEmail(String userid);
}
