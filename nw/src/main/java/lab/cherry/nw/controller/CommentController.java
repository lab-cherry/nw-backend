package lab.cherry.nw.controller;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lab.cherry.nw.error.ErrorResponse;
import lab.cherry.nw.error.ResultResponse;
import lab.cherry.nw.error.enums.SuccessCode;
import lab.cherry.nw.model.CommentEntity;
import lab.cherry.nw.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * <pre>
 * ClassName : CommentController
 * Type : class
 * Description : 댓글 목록 조회, 댓글 상세 조회, 댓글 업데이트, 댓글 삭제, 댓글 찾기 등 댓글과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : CommentRepository, CommentService, CommentServiceImpl
 * </pre>
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board/comment")
@Tag(name = "Comment", description = "Comment API Document")
public class CommentController {
    private final CommentService commentService;

    /**
     * [CommentController] 전체 댓글 목록 함수
     *
     * @return 전체 댓글 목록을 반환합니다.
     *
     * Author : yby654(yby654@github.com)
     */
    @GetMapping("")
    @Operation(summary = "댓글 목록", description = "댓글 목록을 조회합니다.")
    public ResponseEntity<?> findAllComments(
            @RequestParam(required = false) String userid,
            // @RequestParam(defaultValue = "0") Integer page,
            // @RequestParam(defaultValue = "100") Integer size,
			@RequestParam(defaultValue = "created_at,desc") String[] sort) {

            log.info("retrieve all comments controller...!");

             List<CommentEntity> commentEntity =null;
        if(userid == null ) {
            commentEntity = commentService.getComments();
        } else {
            commentEntity = commentService.findByUserId(userid);
        }
        return new ResponseEntity<>(commentEntity, new HttpHeaders(), HttpStatus.OK);
    }
//        for (CommentEntity comment : commentEntity) {
//            comment.sortDataByOrderIndex();
//        }
        

    /**
     * [CommentController] 댓글 생성 함수
     * <pre>
     * @param commentCreateDto 댓글 생성에 필요한 댓글 정보를 담고 있는 객체입니다.
     * @return
     * true  : 업데이트된 댓글 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @PostMapping("")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 생성이 완료되었습니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "입력 값이 잘못 되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Operation(summary = "Comment 생성", description = "Comment를 추가합니다.")
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentEntity.CommentCreateDto commentCreateDto) {
        log.info("[CommentController] createComment...!");
        commentService.createComment(commentCreateDto);

        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
    /**
     * [CommentController] 댓글 수정 함수
     * <pre>
     * @param id 댓글 고유번호를 입력합니다.
     * @param commentUpdateDto 댓글 업데이트에 필요한 댓글 정보를 담고 있는 객체입니다.
     * @return
     * true  : 업데이트된 댓글 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @PatchMapping("/{id}")
    @Operation(summary = "댓글 업데이트", description = "특정 댓글를 업데이트합니다.")
    public ResponseEntity<?> updateById(
            @PathVariable("id") String id,
            @RequestBody CommentEntity.CommentUpdateDto commentUpdateDto) {

        log.info("[CommentController] updateComment...!");

        commentService.updateById(id, commentUpdateDto);

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(commentService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }
    /**
     * [CommentController] 특정 댓글 삭제 함수
     *
     * @param id 댓글 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 댓글를 삭제처리합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @DeleteMapping("{id}")
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {

		log.info("[CommentController] deleteCommentPost...!");

        commentService.deleteById(id);

        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

}