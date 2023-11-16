package lab.cherry.nw.service.Impl;

import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.BoardEntity;
import lab.cherry.nw.model.CommentEntity;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.repository.CommentRepository;
import lab.cherry.nw.service.BoardService;
import lab.cherry.nw.service.CommentService;
import lab.cherry.nw.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * ClassName : CommentServiceImpl
 * Type : class0.20
 * Description : 댓글와 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
@Slf4j
@Service("commentServiceImpl")
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final BoardService boardService;


	/**
     * [CommentServiceImpl] 전체 댓글 조회 함수
     *
     * @return DB에서 전체 댓글 정보 목록을 리턴합니다.
     * @throws EntityNotFoundException 댓글 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 전체 댓글을 조회하여, 댓글 목록을 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @Transactional(readOnly = true)
    @Override
    public List<CommentEntity> getComments() {
    return commentRepository.findAll();
    //        return EntityNotFoundException.requireNotEmpty(orgRepository.findAll(), "Roles Not Found");
}


//
    /**
     * [CommentServiceImpl] 댓글 생성 함수
     *
     * @param commentCreateDto 댓글 생성에 필요한 댓글 등록 정보를 담은 개체입니다.
     * @return 생성된 댓글 정보를 리턴합니다.
//     * @throws CustomException 중복된 이름에 대한 예외 처리 발생
     * <pre>
     * 댓글을 등록합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    public void createComment(CommentEntity.CommentCreateDto commentCreateDto) {
        UserEntity userEntity = userService.findById(commentCreateDto.getUserSeq());
        BoardEntity boardEntity = boardService.findById(commentCreateDto.getBoardSeq());
        Instant instant = Instant.now();
        CommentEntity commentEntity = CommentEntity.builder()
            .user(userEntity)
			.content(commentCreateDto.getContent())
            .board(boardEntity)
            .created_at(instant)
            .build();
        commentRepository.save(commentEntity);
    }

    /**
     * [CommentServiceImpl] 댓글 수정 함수
     *
     * @param id 조회할 댓글의 고유번호입니다.
     * @param commentUpdateDto 댓글 수정에 필요한 사용자 정보를 담은 개체입니다.
     * @throws EntityNotFoundException 사용자 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 특정 댓글에 대해 정보를 수정합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    public void updateById(String id, CommentEntity.CommentUpdateDto commentUpdateDto) {
        Instant instant = Instant.now();
        CommentEntity commentEntity = findById(id);

		if (commentUpdateDto.getContent() != null ) {
			commentEntity = CommentEntity.builder()
			.id(commentEntity.getId())
			.user(commentEntity.getUser())
			.content(commentUpdateDto.getContent())
            .board(commentEntity.getBoard())
            .created_at(commentEntity.getCreated_at())
			.updated_at(instant)
			.build();
            commentRepository.save(commentEntity);

        } else {
            log.error("[CommentServiceImpl - udpateComment] content 만 수정 가능합니다.");
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    /**
     * [CommentServiceImpl] 댓글 삭제 함수
     *
     * @param id 삭제할 댓글의 식별자입니다.
     * @throws EntityNotFoundException 해당 ID의 댓글 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id를 가진 댓글 정보를 삭제합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
     public void deleteById(String id) {
        commentRepository.delete(commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with Id " + id + " Not Found.")));
    }



//    /**
//     * [CommentServiceImpl] 댓글 이름 중복 체크 함수
//     *
//     * @param name 중복 체크에 필요한 댓글 이름 객체입니다.
//     * @throws CustomException 댓글의 이름이 중복된 경우 예외 처리 발생
//     * <pre>
//     * 입력된 댓글 이름으로 이미 등록된 댓글이 있는지 확인합니다.
//     * </pre>
//     *
//     * Author : yby654(yby654@github.com)
//     */
//    @Transactional(readOnly = true)
//    public void checkExistsWithCommentName(String name) {
//        if (commentRepository.findByName(name).isPresent()) {
//            throw new CustomException(ErrorCode.DUPLICATE); // 댓글 이름이 중복됨
//        }
//    }
//
//    /**
//     * [CommentServiceImpl] NAME으로 댓글 조회 함수
//     *
//     * @param name 조회할 댓글의 이름입니다.
//     * @return 주어진 이름에 해당하는 댓글 정보를 리턴합니다.
//     * @throws EntityNotFoundException 해당 이름의 댓글 정보가 없을 경우 예외 처리 발생
//     * <pre>
//     * 입력한 name에 해당하는 댓글 정보를 조회합니다.
//     * </pre>
//     *
//     * Author : yby654(yby654@github.com)
//     */
//    @Transactional(readOnly = true)
//    public CommentEntity findByName(String name) {
//        return commentRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Comment with Name " + name + " Not Found."));
//    }
//
    /**
     * [CommentServiceImpl] ID로 댓글 조회 함수
     *
     * @param id 조회할 댓글의 식별자입니다.
     * @return 주어진 식별자에 해당하는 댓글 정보
     * @throws EntityNotFoundException 해당 ID의 댓글 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id에 해당하는 댓글 정보를 조회합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @Transactional(readOnly = true)
    public CommentEntity findById(String id) {
        return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment with Id " + id + " Not Found."));
    }
    @Transactional(readOnly = true)
    public List<CommentEntity> findByBoardId(String boardSeq) {
        return commentRepository.findByboardid(boardSeq);
    }

     @Transactional(readOnly = true)
    public List<CommentEntity> findByUserId(String boardSeq) {
        return commentRepository.findByuserid(boardSeq);
    }

    
}