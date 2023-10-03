package lab.cherry.nw.service;

import lab.cherry.nw.model.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * ClassName : BoardService
 * Type : interface
 * Description : 게시판와 관련된 함수를 정리한 인터페이스입니다.
 * Related : BoardController, BoardServiceiml
 * </pre>
 */
@Component
public interface BoardService {
 Page<BoardEntity> getBoards(Pageable pageable);
 BoardEntity findById(String id);
// BoardEntity findByUserId(String userid);
// BoardEntity findByOrgId(String orgid);
 void createBoard(BoardEntity.CreateDto boardCreateDto);
 void updateById(String id, BoardEntity.UpdateDto updateDto);
 void deleteById(String id);
 Page<BoardEntity> findPageByUserId(String userid, Pageable pageable);
// void updateOrgById(String id, List<String> orgIds);
}