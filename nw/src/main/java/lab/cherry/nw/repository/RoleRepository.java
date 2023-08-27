package lab.cherry.nw.repository;

import lab.cherry.nw.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * <pre>
 * ClassName : RoleRepository
 * Type : interface
 * Descrption : Role JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query("select r from RoleEntity r WHERE r.name = ?1")
    Optional<RoleEntity> findByName(String name);

    @Override
    @Query("select r from RoleEntity r WHERE r.id = ?1")
    void deleteById(Long id);

    @Query("select count(*) from RoleEntity r")
    long countRole();
}