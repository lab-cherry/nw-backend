package com.innogrid.medge.service;

import com.innogrid.medge.model.dto.UserLoginDto;
import com.innogrid.medge.model.dto.UserRegisterDto;
import com.innogrid.medge.util.Security.AccessToken;
import org.springframework.stereotype.Component;


@Component
public interface AuthService {
    AccessToken register(UserRegisterDto userRegisterDto);
    AccessToken login(UserLoginDto userLoginDto);
}
