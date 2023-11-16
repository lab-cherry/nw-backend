package lab.cherry.nw.service;
import lab.cherry.nw.model.CommentEntity;

import org.springframework.stereotype.Component;
import java.util.List;

/**
 * <pre>
 * ClassName : CommentService
 * Type : interface
 * Description : 댓글와 관련된 함수를 정리한 인터페이스입니다.
 * Related : CommentController, CommentServiceiml
 * </pre>
 */
@Component
public interface CommentService {
 List<CommentEntity> getComments();
 List<CommentEntity> findByBoardId(String boardSeq);
// CommentEntity findByUserId(String userid);
CommentEntity findById(String id);
 void createComment(CommentEntity.CommentCreateDto commentCreateDto);
 void updateById(String id, CommentEntity.CommentUpdateDto commentUpdateDto);
 void deleteById(String id);
 List<CommentEntity> findByUserId(String userSeq);
// void updateOrgById(String id, List<String> orgIds);
}