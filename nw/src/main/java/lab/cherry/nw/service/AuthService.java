package lab.cherry.nw.service;

import lab.cherry.nw.model.dto.UserLoginDto;
import lab.cherry.nw.model.dto.UserRegisterDto;
import lab.cherry.nw.util.Security.AccessToken;
import org.springframework.stereotype.Component;


@Component
public interface AuthService {
    AccessToken register(UserRegisterDto userRegisterDto);
    AccessToken login(UserLoginDto userLoginDto);
}
