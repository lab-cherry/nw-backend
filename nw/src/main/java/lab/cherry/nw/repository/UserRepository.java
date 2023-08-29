package lab.cherry.nw.repository;

import lab.cherry.nw.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


/**
 * <pre>
 * ClassName : UserRepository
 * Type : interface
 * Descrption : User JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-jpa, UserServiceImpl, AuthServiceImpl, CustomUserDetailsService
 * </pre>
 */
//@Repository
public interface UserRepository extends MongoRepository<UserEntity, UUID> {

    Page<UserEntity> findAll(Pageable pageable);

    Page<UserEntity> findPageByUserid(String userid, Pageable pageable);

    Optional<UserEntity> findById(String id);

    Optional<UserEntity> findByuserid(String userid);

    Optional<UserEntity> findByUsername(String username);

    void deleteById(UUID id);
}
