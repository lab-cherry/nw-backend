package com.innogrid.medge.service.Impl;

import com.innogrid.medge.error.enums.ErrorCode;
import com.innogrid.medge.error.exception.CustomException;
import com.innogrid.medge.error.exception.EntityNotFoundException;
import com.innogrid.medge.model.RoleEntity;
import com.innogrid.medge.model.UserEntity;
import com.innogrid.medge.model.dto.UserLoginDto;
import com.innogrid.medge.model.dto.UserRegisterDto;
import com.innogrid.medge.repository.RoleRepository;
import com.innogrid.medge.repository.UserRepository;
import com.innogrid.medge.service.AuthService;
import com.innogrid.medge.util.Security.AccessToken;
import com.innogrid.medge.util.Security.jwt.IJwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service("authServiceImpl")
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final IJwtTokenProvider iJwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;


    public AccessToken register(UserRegisterDto userRegisterDto) {
        Date date = new Date();
        checkUserExistsWithUserName(userRegisterDto.getUsername());

        UserEntity userEntity = UserEntity.builder()
            .username(userRegisterDto.getUsername())
            .email(userRegisterDto.getEmail())
            .password(passwordEncoder.encode(userRegisterDto.getPassword()))
            .roles(getRoles(userRegisterDto.getRoles()))
            .enabled(true)
            .created_at(new Timestamp(date.getTime()))
            .build();

        userRepository.save(userEntity);

        String username = userEntity.getUsername();
        Set<RoleEntity> roles = userEntity.getRoles();

        return iJwtTokenProvider.createJwtToken(username,roles);
    }


    @Transactional(readOnly = true)
    public AccessToken login(UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        log.error("username = {}", userLoginDto.getUsername());
        log.error("password = {}", userLoginDto.getPassword());

        authenticateByIdAndPassword(userLoginDto);
        Set<RoleEntity> roles = userRepository.findByUserName(username).get().getRoles();
        return iJwtTokenProvider.createJwtToken(userLoginDto.getUsername(), roles);


//        try {
//            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
//            Set<RoleEntity> roles = userRepository.findByUserName(username).get().getRoles();
//            return iJwtTokenProvider.createJwtToken(username,roles);
//
//        }catch (AuthenticationException exception) {
//            throw new CustomException(ErrorCode.RUNTIME_EXCEPTION);
//        }
    }
    private void checkUserExistsWithUserName(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new CustomException(ErrorCode.DUPLICATE);
        }
    }
    private void authenticateByIdAndPassword(UserLoginDto userLoginDto) {
        UserEntity user = userRepository.findByUserName(userLoginDto.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        if(!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }
    private Set<RoleEntity> getRoles(String [] roles){
        Set<RoleEntity> userRoles = new HashSet<>();
        for(String role : roles) {
            userRoles.add(roleRepository.findByName(role));
        }
        return userRoles;
    }
}
