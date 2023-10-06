package lab.cherry.nw.repository;

import lab.cherry.nw.model.PyebaeksilEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

/**
 * <pre>
 * ClassName : PyebaeksilRepository
 * Type : interface
 * Descrption : 폐백실 관리 JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-mongo
 * </pre>
 */
//@Repository
public interface PyebaeksilRepository extends MongoRepository<PyebaeksilEntity, String> {

	Page<PyebaeksilEntity> findPageByName(String name, Pageable pageable);
	Optional<PyebaeksilEntity> findByName(String name);

}