package lab.cherry.nw.repository;

import lab.cherry.nw.model.BanquetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

/**
 * <pre>
 * ClassName : BanquetRepository
 * Type : interface
 * Descrption : 연회장 관리 JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-mongo
 * </pre>
 */
//@Repository
public interface BanquetRepository extends MongoRepository<BanquetEntity, String> {

	Page<BanquetEntity> findPageByName(String name, Pageable pageable);
	Optional<BanquetEntity> findByName(String name);

}