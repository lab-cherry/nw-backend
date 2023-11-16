package lab.cherry.nw.repository;
import lab.cherry.nw.model.CommentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

/**
 * <pre>
 * ClassName : CommentRepository
 * Type : interface
 * Descrption : 댓글 JPA 구현을 위한 인터페이스입니다.
 * Related : CommentServiceImpl, CommentService
 * </pre>
 */
public interface CommentRepository extends MongoRepository<CommentEntity, String> {

    List<CommentEntity> findAll();
    @Query("{'board.$_id' : ?0}")
    List<CommentEntity> findByboardid(String boradseq);
    @Query("{'user.$_id' : ?0}")
    List<CommentEntity> findByuserid(String userSeq);
    Optional<CommentEntity> findById(String id);
    // @Query("{'board.$_id' : ?0}")
    // Optional<List<CommentEntity>> findByuserid(String boradseq);
    void deleteById(UUID id);
}