package lab.cherry.nw.repository;

import lab.cherry.nw.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.Optional;

/**
 * <pre>
 * ClassName : UserRepository
 * Type : interface
 * Descrption : User JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-mongo, UserServiceImpl, AuthServiceImpl, CustomUserDetailsService
 * </pre>
 */
//@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

    Page<UserEntity> findAll(Pageable pageable);
    Page<UserEntity> findPageByUserid(String userid, Pageable pageable);
    @Query("{'org.$_id' : ?0}")
    Page<UserEntity> findPageByOrgseq(String orgseq, Pageable pageable);
    Optional<UserEntity> findById(String id);
    Optional<UserEntity> findByuserid(String userid);
    Optional<UserEntity> findByUsername(String username);
    @Query("{'userid' : ?0, 'email' : ?1}")
    Optional<UserEntity> findByuseridAndEmail(String userid, String email);
    
}
