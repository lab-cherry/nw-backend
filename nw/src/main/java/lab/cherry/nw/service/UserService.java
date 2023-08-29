package lab.cherry.nw.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import lab.cherry.nw.model.UserEntity;
import java.util.List;
import java.util.UUID;


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
    Page<UserEntity> getUsers(Pageable pageable);
    UserEntity findById(String id);
    UserEntity findByUserId(String userid);
    void updateById(String id, UserEntity.UpdateDto user);
    void deleteById(String id);
    Page<UserEntity> findPageByUserId(String userid, Pageable pageable);
}
