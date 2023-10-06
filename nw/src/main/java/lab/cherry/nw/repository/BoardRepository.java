package lab.cherry.nw.repository;

import lab.cherry.nw.model.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * <pre>
 * ClassName : BoardRepository
 * Type : interface
 * Descrption : 게시판 JPA 구현을 위한 인터페이스입니다.
 * Related : BoardServiceImpl, BoardService
 * </pre>
 */
public interface BoardRepository extends MongoRepository<BoardEntity, UUID> {

    Page<BoardEntity> findAll(Pageable pageable);
    Page<BoardEntity> findPageByUserid(String userid, Pageable pageable);

    Optional<BoardEntity> findById(String id);

    Optional<BoardEntity> findByuserid(String userid);
    void deleteById(UUID id);
}