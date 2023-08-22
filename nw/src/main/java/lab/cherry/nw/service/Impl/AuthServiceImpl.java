package lab.cherry.nw.service.Impl;

import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.RoleEntity;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.repository.RoleRepository;
import lab.cherry.nw.repository.UserRepository;
import lab.cherry.nw.service.AuthService;
import lab.cherry.nw.service.RoleService;
import lab.cherry.nw.util.Security.AccessToken;
import lab.cherry.nw.util.Security.jwt.IJwtTokenProvider;
import lab.cherry.nw.util.TsidGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * <pre>
 * ClassName : AuthServiceImpl
 * Type : class
 * Description : 인증과 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
@Slf4j
@Service("authServiceImpl")
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final IJwtTokenProvider iJwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleService roleService;

    /**
     * [AuthServiceImpl] 회원가입 함수
     *
     * @param userRegisterDto 회원가입에 필요한 사용자 등록 정보를 담은 개체입니다.
     * @return 생성된 JWT 토큰을 리턴합니다.
     * @throws CustomException 중복된 아이디에 대한 예외 처리 발생
     * <pre>
     * 사용자를 등록하고, 등록된 사용자의 정보를 기반으로 JWT 토큰을 생성하여 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public AccessToken register(UserEntity.RegisterDto userRegisterDto) {

        Instant instant = Instant.now();
        checkExistsWithUserName(userRegisterDto.getUsername()); // 동일한 이름 중복체크


        // TODO: 추후 Production 시, 삭제 후 First DB Migration 으로 대체 예정 (테스트 용도)
        RoleEntity roleEntity = initRole();

        UserEntity userEntity = UserEntity.builder()
            .id(TsidGenerator.next())
            .username(userRegisterDto.getUsername())
            .email(userRegisterDto.getEmail())
            .password(passwordEncoder.encode(userRegisterDto.getPassword()))
            .role(roleEntity)
            .enabled(true)
            .created_at(Timestamp.from(instant))
            .build();

        userRepository.save(userEntity);

        String username = userEntity.getUsername();
        RoleEntity role = userEntity.getRole();

        return iJwtTokenProvider.createJwtToken(username,role);
    }

    /**
     * [AuthServiceImpl] 로그인 함수
     *
     * @param userLoginDto 로그인에 필요한 사용자 등록 정보를 담은 개체입니다.
     * @return 생성된 JWT 토큰을 리턴합니다.
     * @throws CustomException 입력 값이 유효하지 않거나 사용자가 등록되지 않았을 경우 예외 처리 발생
     * <pre>
     * 사용자의 로그인 정보를 검증하고, 검증된 사용자의 정보를 기반으로 JWT 토큰을 생성하여 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public AccessToken login(UserEntity.LoginDto userLoginDto) {
        if(userLoginDto == null) {  // Body 값이 비어 있을 경우, 예외처리
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);   // 입력 값이 유효하지 않음
        }

        authenticateByIdAndPassword(userLoginDto);

        RoleEntity role = userRepository.findByUserName(userLoginDto.getUsername()).get().getRole();
        return iJwtTokenProvider.createJwtToken(userLoginDto.getUsername(), role);

    }

    /**
     * [AuthServiceImpl] 아이디 중복 체크 함수
     *
     * @param username 중복 체크에 필요한 사용자 이름 객체입니다.
     * @throws CustomException 사용자의 이름이 중복된 경우 예외 처리 발생
     * <pre>
     * 입력된 사용자 이름으로 이미 등록된 사용자가 있는지 확인합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public void checkExistsWithUserName(String username) {
        if (userRepository.findByUserName(username).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE); // 사용자 아이디가 중복됨
        }
    }

    /**
     * [AuthServiceImpl] 로그인 정보 검증 함수
     *
     * @param userLoginDto userLoginDto 로그인 정보 검증에 필요한 사용자 등록 정보를 담은 개체입니다.
     * @throws CustomException 사용자가 등록되지 않았거나, 비밀번호가 일치하지 않을 경우 예외 처리 발생
     * <pre>
     * 입력된 사용자 로그인 정보를 검증합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    private void authenticateByIdAndPassword(UserEntity.LoginDto userLoginDto) {
        UserEntity user = userRepository.findByUserName(userLoginDto.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));   // 로그인 정보가 유효하지 않음

        if(!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);   // 로그인 정보가 정확하지 않음
        }
    }

    /**
     * [AuthServiceImpl] 역할 정보 조회 함수
     *
     * @param roles Roles 정보를 포함하고 있습니다.
     * @return Roles 정보를 담은 Set<Entity>
     * <pre>
     * 입력된 사용자 정보를 조회하여, Set<RoleEntity> 형태로 역할을 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    private Set<RoleEntity> getRoles(String [] roles){
    Set<RoleEntity> userRoles = new HashSet<>();
    for (String role : roles) {
        Optional<RoleEntity> optionalRole = roleRepository.findByName(role);
        if (optionalRole.isPresent()) {
            userRoles.add(optionalRole.get()); // Extract the RoleEntity from Optional
        } else {
            // Handle the case when role is not found
            // You can throw an exception or perform other error handling
        }
    }
    return userRoles;
    }

    // TODO: 추후 Production 시, 삭제 후 First DB Migration 으로 대체 예정 (테스트 용도), ROLE_USER 없을 시, "ROLE_USER" 자동 추가
    private RoleEntity initRole() {

        if (roleRepository.findByName("ROLE_USER").isEmpty()) {

            RoleEntity roleEntity = RoleEntity.builder()
            .name("ROLE_USER")
            .build();

            return roleRepository.save(roleEntity);

        }
        return null;
    }
}
