package lab.cherry.nw.service;

import lab.cherry.nw.model.UserEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
    Boolean checkId(String id);
	UserEntity findByUserId(String userid);
    void updateById(String id, UserEntity.UserUpdateDto user);
    void deleteById(String id);
    Page<UserEntity> findPageByUserId(String userid, Pageable pageable);
	void updateOrgById(String id, String orgId);
    void updateUserPhoto(String id, List<MultipartFile> image);
    void updateEmailVerifiedByid(String id);
}
