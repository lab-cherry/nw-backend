package lab.cherry.nw.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import lab.cherry.nw.model.UserCardEntity;


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
    UserCardEntity findByUserCardId(String userSeq);
    UserCardEntity createUserCard(UserCardEntity.UserCardCreateDto userCardCreateDto);
    void updateById(String id, UserCardEntity.UserCardUpdateDto usercard);
    void updateWeddinghallByName(String id, String weddinghall);
    void deleteById(String id);
    Page<UserCardEntity> findPageById(String id, Pageable pageable);

//    Page<UserCardEntity> findPageByName(String name, Pageable pageable);
}
