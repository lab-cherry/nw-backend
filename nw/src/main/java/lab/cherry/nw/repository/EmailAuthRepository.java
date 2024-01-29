package lab.cherry.nw.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import lab.cherry.nw.model.EmailAuthEntity;

/**
 * <pre>
 * ClassName : EmailAuthRepository
 * Type : interface
 * Descrption : 이메일 인증 관련 JPA 구현을 위한 인터페이스입니다.
 * Related : spring-boot-starter-data-mongo
 * </pre>
 */
//@Repository
public interface EmailAuthRepository extends MongoRepository<EmailAuthEntity, String> {

    @Query("{'email' : ?0}")
    Optional<EmailAuthEntity> findByEmail(String email);
    @Query("{'userid' : ?0, 'email' : ?1}")
    Optional<EmailAuthEntity> findByUseridAndEmail(String userid, String email);
    @Query("{'email' : ?0, 'token' : ?1}")
    Optional<EmailAuthEntity> findByEmailAndToken(String email, String token);
    
}