package lab.cherry.nw.repository;

import lab.cherry.nw.model.QsheetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * <pre>
 * ClassName : QsheetRepository
 * Type : interface
 * Descrption : 큐시트 JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
@Repository
public interface QsheetRepository extends JpaRepository<QsheetEntity, Long> {

    @Query("select o from OrgEntity o WHERE o.name = ?1")
    Optional<QsheetEntity> findByName(String name);

    @Override
    @Query("select o from QsheetEntity o")
    List<QsheetEntity> findAll();

    @Override
    @Query("select o from OrgEntity o WHERE o.id = ?1")
    void deleteById(Long id);
}