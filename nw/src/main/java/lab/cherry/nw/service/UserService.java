package lab.cherry.nw.service;

import org.springframework.stereotype.Component;
import lab.cherry.nw.model.UserEntity;
import java.util.List;


/**
 * <pre>
 * ClassName : UserService
 * Type : interface
 * Description : User와 관련된 함수를 정리한 인터페이스입니다.
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
