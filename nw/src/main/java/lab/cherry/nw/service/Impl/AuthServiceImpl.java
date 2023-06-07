package lab.cherry.nw.service.Impl;

import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.model.RoleEntity;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.model.dto.UserLoginDto;
import lab.cherry.nw.model.dto.UserRegisterDto;
import lab.cherry.nw.repository.RoleRepository;
import lab.cherry.nw.repository.UserRepository;
import lab.cherry.nw.service.AuthService;
import lab.cherry.nw.util.Security.AccessToken;
import lab.cherry.nw.util.Security.jwt.IJwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
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
        
        log.error("username = {}", userLoginDto.getUsername());
        log.error("password = {}", userLoginDto.getPassword());

        authenticateByIdAndPassword(userLoginDto);
        Set<RoleEntity> roles = userRepository.findByUserName(userLoginDto.getUsername()).get().getRoles();
        return iJwtTokenProvider.createJwtToken(userLoginDto.getUsername(), roles);

    }
    private void checkUserExistsWithUserName(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new CustomException(ErrorCode.DUPLICATE); // 중복된 아이디
        }
    }
    private void authenticateByIdAndPassword(UserLoginDto userLoginDto) {
        UserEntity user = userRepository.findByUserName(userLoginDto.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));   // 로그인 요청 시, 등록되지 않은 아이디 처리

        if(!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);   // 로그인 요청 시, 비밀번호가 잘못된 경우 처리
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
