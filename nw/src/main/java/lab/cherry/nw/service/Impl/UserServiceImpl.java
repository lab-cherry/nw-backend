package lab.cherry.nw.service.Impl;

import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.repository.UserRepository;
import lab.cherry.nw.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <pre>
 * ClassName : UserServiceImpl
 * Type : class
 * Description : 사용자와 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
@Slf4j
@Service("userServiceImpl")
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * [UserServiceImpl] 전체 사용자 조회 함수
     *
     * @return DB에서 전체 사용자 정보 목록을 리턴합니다.
     * @throws EntityNotFoundException 사용자 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 전체 사용자를 조회하여, 사용자 정보 목록을 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    @Override
    public Page<UserEntity> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
        //        return EntityNotFoundException.requireNotEmpty(userRepository.findAll(pageable), "Users Not Found");
    }

    /**
     * [UserServiceImpl] 아이디로 사용자 조회 함수
     *
     * @param userid 조회할 사용자의 아이디입니다.
     * @return 주어진 아이디에 해당하는 사용자 정보를 리턴합니다.
     * @throws EntityNotFoundException 해당 아이디의 사용자 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 userid에 해당하는 사용자 정보를 조회합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public UserEntity findByUserId(String userid) {
        return userRepository.findByUserId(userid).orElseThrow(() -> new EntityNotFoundException("User with userId " + userid + " Not Found."));
    }

    /**
     * [UserServiceImpl] 사용자 수정 함수
     *
     * @param user 사용자 수정에 필요한 사용자 정보를 담은 개체입니다.
     * @throws EntityNotFoundException 사용자 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 특정 사용자에 대해 사용자 정보를 수정합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public void updateById(Long tsid, UserEntity.UpdateDto user) {

        UserEntity userEntity = findById(tsid);

        if (user.getUsername() != null || user.getEmail() != null || user.getPassword() != null) {

            String username = (user.getUsername() != null) ? user.getUsername() : userEntity.getUsername();
            String email = (user.getEmail() != null) ? user.getEmail() : userEntity.getEmail();
            String password = (user.getPassword() != null) ? passwordEncoder.encode(user.getPassword()) : userEntity.getPassword();

            userRepository.updateUser(tsid, username, email, password);

        } else {
            log.error("[UserServiceImpl - udpateUser] userName, userEmail, userPassword만 수정 가능합니다.");
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    /**
     * [UserServiceImpl] 사용자 삭제 함수
     *
     * @param tsid 삭제할 사용자의 식별자입니다.
     * @throws EntityNotFoundException 해당 ID의 사용자 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id를 가진 사용자 정보를 삭제합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public void deleteById(Long tsid) {
        userRepository.delete(userRepository.findById(tsid).orElseThrow(() -> new EntityNotFoundException("User with Id " + tsid + " Not Found.")));
    }

    /**
     * [UserServiceImpl] ID로 사용자 조회 함수
     *
     * @param id 조회할 사용자의 식별자입니다.
     * @return 주어진 식별자에 해당하는 사용자 정보
     * @throws EntityNotFoundException 해당 ID의 사용자 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id에 해당하는 사용자 정보를 조회합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with Id " + id + " Not Found."));
    }

    @Transactional(readOnly = true)
    public Page<UserEntity> findPageByUserId(String userid, Pageable pageable) {
        return userRepository.findPageByUserId(userid, pageable);
    }
}
