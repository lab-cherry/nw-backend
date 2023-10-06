package lab.cherry.nw.service;

import lab.cherry.nw.model.OrgEntity;
import lab.cherry.nw.model.UserCardEntity;
import lab.cherry.nw.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * <pre>
 * ClassName : UserCardService
 * Type : interface
 * Description : User와 관련된 함수를 정리한 인터페이스입니다.
 * Related : UserController, UserServiceImpl
 * </pre>
 */
@Component
public interface UserCardService {
    Page<UserCardEntity> getUsercards(Pageable pageable);
    UserCardEntity findById(String id);
//    UserCardEntity findByUserCardId(String id);
    UserCardEntity createUserCard(UserCardEntity.UserCardCreateDto userCardCreateDto);
    void updateById(String id, UserCardEntity.UserCardUpdateDto usercard);
    void deleteById(String id);
    Page<UserCardEntity> findPageById(String id, Pageable pageable);

//    Page<UserCardEntity> findPageByName(String name, Pageable pageable);
}
