package lab.cherry.nw.service.Impl;

import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.EmailAuthEntity;
import lab.cherry.nw.model.RoleEntity;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.repository.EmailAuthRepository;
import lab.cherry.nw.repository.RoleRepository;
import lab.cherry.nw.repository.UserRepository;
import lab.cherry.nw.service.AuthService;
import lab.cherry.nw.service.EmailAuthService;
import lab.cherry.nw.service.TokenService;
import lab.cherry.nw.service.UserService;
import lab.cherry.nw.util.Security.AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

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

    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailAuthRepository emailAuthRepository;
    private final EmailAuthService emailAuthService;
    private final UserService userService;

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
    public UserEntity register(UserEntity.UserRegisterDto userRegisterDto) {

        Instant instant = Instant.now();
        ObjectId objectId = new ObjectId();
        ObjectId verifiedObjectId = new ObjectId();
        checkExistsWithUserId(userRegisterDto.getUserId()); // 동일한 이름 중복체크

        RoleEntity roleEntity = roleRepository.findByName("ROLE_USER").get();

        UserEntity userEntity = UserEntity.builder()
            .id(objectId.toString())
            .userid(userRegisterDto.getUserId())
            .username(userRegisterDto.getUserName())
            .email(userRegisterDto.getUserEmail())
            .password(passwordEncoder.encode(userRegisterDto.getUserPassword()))
            .role(roleEntity)
            .enabled(true)
            .isEmailVerified(false)
            .created_at(instant)
            .build();

        UserEntity user = userRepository.save(userEntity);


        EmailAuthEntity emailAuthEntity  = EmailAuthEntity.builder()
            .id(objectId.toString())
            .user(user)
            .email(userRegisterDto.getUserEmail())
            .token(verifiedObjectId.toString())
            .expired(LocalDateTime.now().plusMinutes(5L))
            .build();

        emailAuthRepository.save(emailAuthEntity);

        emailAuthService.ConfirmEmailSend(emailAuthEntity.getEmail(), emailAuthEntity.getToken());

        return userEntity;
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
    public AccessToken.Get login(UserEntity.UserLoginDto userLoginDto) {

        authenticateByIdAndPassword(userLoginDto);
        isValidEmailVertified(userLoginDto);

        Optional<UserEntity> userEntity = userRepository.findByuserid(userLoginDto.getUserId());
        AccessToken accessToken = tokenService.generateJwtToken(userLoginDto.getUserId(), userEntity.get().getRole());

        Map<String, String> info = new HashMap<>();
        info.put("userSeq", userEntity.get().getId());
        info.put("orgSeq", (userEntity.get().getOrg() == null) ? null : userEntity.get().getOrg().getId());
        info.put("roleSeq", (userEntity.get().getRole() == null) ? null : userEntity.get().getRole().getId());

        return AccessToken.Get.builder()
            .userSeq(userEntity.get().getId())
            .userId(userEntity.get().getUserid())
            .userName(userEntity.get().getUsername())
            .userRole((userEntity.get().getRole() == null) ? "ROLE_USER" : userEntity.get().getRole().getName())
            .info(info)
            .accessToken(accessToken.getAccessToken())
            .build();

    }

    /**
     * [AuthServiceImpl] 아이디 중복 체크 함수
     *
     * @param userid 중복 체크에 필요한 사용자 아이디 객체입니다.
     * @throws CustomException 사용자의 아이디가 중복된 경우 예외 처리 발생
     * <pre>
     * 입력된 사용자 아아디로 이미 등록된 사용자가 있는지 확인합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public void checkExistsWithUserId(String userid) {
        if (userRepository.findByuserid(userid).isPresent()) {
            throw new CustomException(ErrorCode.USER_DUPLICATE); // 사용자 아이디가 중복됨
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
    private void authenticateByIdAndPassword(UserEntity.UserLoginDto userLoginDto) {

        if(userLoginDto == null) {  // Body 값이 비어 있을 경우, 예외처리
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);   // 입력 값이 유효하지 않음
        }

        UserEntity user = userRepository.findByuserid(userLoginDto.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));   // 로그인 정보가 유효하지 않음

        if(!passwordEncoder.matches(userLoginDto.getUserPassword(), user.getPassword())) {
            log.error("{} Account Password is Corrent!", userLoginDto.getUserId());
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);   // 로그인 정보가 정확하지 않음
        }
    }

    /**
     * [AuthServiceImpl] 이메일 인증 여부 확인 함수
     *
     * @param userLoginDto userLoginDto 로그인 정보 검증에 필요한 사용자 등록 정보를 담은 개체입니다.
     * @throws CustomException 사용자가 등록되지 않았거나, 비밀번호가 일치하지 않을 경우 예외 처리 발생
     * <pre>
     * 입력된 사용자 로그인 정보를 검증합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    private void isValidEmailVertified(UserEntity.UserLoginDto userLoginDto) {

        if(userLoginDto == null) {  // Body 값이 비어 있을 경우, 예외처리
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);   // 입력 값이 유효하지 않음
        }

        UserEntity user = userRepository.findByuserid(userLoginDto.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));   // 로그인 정보가 유효하지 않음

        if(!user.getIsEmailVerified()) {
            log.error("{}는 이메일 인증이 필요합니다.", userLoginDto.getUserId());
            throw new CustomException(ErrorCode.REQUIRE_EMAIL_VERIFIED);   // 이메일 인증이 필요합니다.
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

	public UserEntity myInfo() {

		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();

        log.error("authentication.getName() {}", authentication.getName());

        if(authentication.getName() == null || authentication.getName() == "anonymousUser") {
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }
        

		UserEntity user = userRepository.findByuserid(authentication.getName()).get();
		return user;

	}

    @Transactional(readOnly = true)
    public void confirmEmail(String email, String token) {

        EmailAuthEntity emailAuthEntity = emailAuthRepository.findByEmailAndToken(email, token)
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_AUTH_ERROR));

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime expiredDate = emailAuthEntity.getExpired();

        if (currentDate.isAfter(expiredDate)) {
            throw new CustomException(ErrorCode.EXPIRED_EXCEPTION);
        }

        userService.updateEmailVerifiedByid(emailAuthEntity.getUser().getId());
    }

        @Transactional(readOnly = true)
    public UserEntity findByuserid(String userid) {
        return userRepository.findByuserid(userid).orElseThrow(() -> new EntityNotFoundException("User with userId " + userid + " Not Found."));
    }

    @Transactional(readOnly = true)
    public void reConfirmEmail(String userSeq) {

        EmailAuthEntity emailAuthEntity = emailAuthRepository.findById(userSeq)
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_AUTH_ERROR));
        
        emailAuthEntity = emailAuthService.updateExpired(userSeq);
        emailAuthService.ConfirmEmailSend(emailAuthEntity.getEmail(), emailAuthEntity.getToken());

    }

    public void forgotPassword(String userid, String email) {

        UserEntity userEntity = userRepository.findByuseridAndEmail(userid, email).orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        log.error("userEntity getUsername {}", userEntity.getUsername());
        log.error("userEntity getEmail {}", userEntity.getEmail());

        ObjectId objectId = new ObjectId();
        userEntity.resetPassword(passwordEncoder.encode(objectId.toString()));
        userRepository.save(userEntity);

        emailAuthService.ResetPasswordSend(email, objectId.toString());
    }
}
