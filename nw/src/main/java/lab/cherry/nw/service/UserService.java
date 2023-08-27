package lab.cherry.nw.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Page<UserEntity> getUsers(Pageable pageable);
    UserEntity findById(Long tsid);
    UserEntity findByUserId(String userid);
    void updateById(Long tsid, UserEntity.UpdateDto user);
    void deleteById(Long tsid);
    Page<UserEntity> findPageByUserId(String userid, Pageable pageable);
}
