package lab.cherry.nw.repository;

import com.github.f4b6a3.tsid.Tsid;
import lab.cherry.nw.model.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * <pre>
 * ClassName : UserRepository
 * Type : interface
 * Descrption : User JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-jpa, UserServiceImpl, AuthServiceImpl, CustomUserDetailsService
 * </pre>
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Override
    @Query("select u from UserEntity u WHERE u.id = ?1")
    Optional<UserEntity> findById(Long id);

    @Query("select u from UserEntity u WHERE u.userid = ?1")
    Page<UserEntity> findPageByUserId(String userid, Pageable pageable);

    @Query("select u from UserEntity u WHERE u.userid = ?1")
    Optional<UserEntity> findByUserId(String userid);

    @Override
    @Query("select u from UserEntity u WHERE u.id = ?1")
    void deleteById(Long id);

    @Modifying
    @Query("update UserEntity u set u.username = :username, u.email = :email, u.password = :password where u.id = :id")
    void updateUser(
            @Param("id") Long id,
            @Param("username") String username,
            @Param("email") String email,
            @Param("password") String password
    );
}
