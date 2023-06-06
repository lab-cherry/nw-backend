package lab.cherry.nw.repository;

import lab.cherry.nw.model.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("select u from UserEntity u WHERE u.username = ?1")
    Optional<UserEntity> findByUserName(String username);

    @Override
    @Query("select u from UserEntity u")
    List<UserEntity> findAll();

    boolean existsByUsername(String username);

//    @EntityGraph(attributePaths = "roles")
//    @Query("select u from UserEntity u LEFT JOIN FETCH u.roles WHERE u.username = ?1")
//    Optional<UserEntity> findByUserNameAuth(String username);
}
