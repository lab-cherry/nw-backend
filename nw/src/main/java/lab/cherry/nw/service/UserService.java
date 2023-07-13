package lab.cherry.nw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lab.cherry.nw.model.UserEntity;


/**
 * <pre>
 * ClassName : UserService
 * Type : interface
 * Descrption : User와 관련된 함수를 정리한 인터페이스입니다.
 * Related : UserController, UserServiceImpl
 * </pre>
 */
@Component
public interface UserService {
    List<UserEntity> getUsers();
    void updateUser(UserEntity user);
    void deleteUser(Integer id);
    UserEntity findById(Integer id);
    UserEntity findByUserName(String username);
}
