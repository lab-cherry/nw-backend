package lab.cherry.nw.repository;

import lab.cherry.nw.model.OrgEntity;
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
 * ClassName : OrgRepository
 * Type : interface
 * Descrption : 조직 JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
@Repository
public interface OrgRepository extends JpaRepository<OrgEntity, Long> {

    @Override
    @Query("select o from OrgEntity o WHERE o.id = ?1")
    Optional<OrgEntity> findById(Long id);

    @Query("select o from OrgEntity o WHERE o.name = ?1")
    Optional<OrgEntity> findByName(String name);

    @Query("select o from OrgEntity o WHERE o.name = ?1")
    Page<OrgEntity> findPageByName(String name, Pageable pageable);

    @Override
    @Query("select o from OrgEntity o WHERE o.id = ?1")
    void deleteById(Long id);

    @Modifying
    @Query("update OrgEntity o set o.name = :name, o.biznum = :biznum, o.contact = :contact where o.id = :id")
    void updateOrganization(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("biznum") String biznum,
            @Param("contact") String contact
    );
}