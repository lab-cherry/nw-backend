package lab.cherry.nw.repository;

import lab.cherry.nw.model.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


/**
 * <pre>
 * ClassName : RoleRepository
 * Type : interface
 * Descrption : Role JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
//@Repository
public interface RoleRepository extends MongoRepository<RoleEntity, UUID> {

    Page<RoleEntity> findAll(Pageable pageable);

    Page<RoleEntity> findPageByName(String rolename, Pageable pageable);

    Optional<RoleEntity> findById(String id);

    Optional<RoleEntity> findByName(String rolename);

    void deleteById(UUID id);

}