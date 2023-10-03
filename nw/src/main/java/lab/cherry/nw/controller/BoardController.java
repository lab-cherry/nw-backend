package lab.cherry.nw.controller;

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
import lab.cherry.nw.model.BoardEntity;
import lab.cherry.nw.service.BoardService;
import lab.cherry.nw.util.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * ClassName : BoardController
 * Type : class
 * Description : 게시판 목록 조회, 게시판 상세 조회, 게시판 업데이트, 게시판 삭제, 게시판 찾기 등 게시판과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : BoardRepository, BoardService, BoardServiceImpl
 * </pre>
 */

// MEMO : 재사용성을 위해 ServiceImpl에서만 비즈니스 로직을 사용하기로 함
//        Dto를 통해 알맞는 파라미터로 데이터 가공 후 사용하기로 함
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
@Tag(name = "Board", description = "Board API Document")
public class BoardController {
    private final BoardService boardService;

    /**
     * [BoardController] 전체 게시판 목록 함수
     *
     * @return 전체 게시판 목록을 반환합니다.
     *
     * Author : yby654(yby654@github.com)
     */
    @GetMapping("/post")
    @Operation(summary = "게시판 목록", description = "게시판 목록을 조회합니다.")
    public ResponseEntity<?> findAllBoards(
            @RequestParam(required = false) String userid,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
			@RequestParam(defaultValue = "created_at,desc") String[] sort) {

    log.info("retrieve all boards controller...!");

    Pageable pageable = PageRequest.of(page, size, Sort.by(Common.getOrder(sort)));

    Page<BoardEntity> boardEntity;
    if(userid == null ) {
        boardEntity = boardService.getBoards(pageable);
    } else {
        boardEntity = boardService.findPageByUserId(userid, pageable);
    }
//        for (BoardEntity board : boardEntity) {
//            board.sortDataByOrderIndex();
//        }
        return new ResponseEntity<>(boardEntity, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [BoardController] 게시물 생성 함수
     * <pre>
     * @param boardCreateDto 게시물 생성에 필요한 게시판 정보를 담고 있는 객체입니다.
     * @return
     * true  : 업데이트된 게시물 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @PostMapping("/post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 생성이 완료되었습니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "입력 값이 잘못 되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Operation(summary = "Board 생성", description = "Board를 추가합니다.")
    public ResponseEntity<?> createBoard(@Valid @RequestBody BoardEntity.CreateDto boardCreateDto) {
        log.info("[BoardController] createBoard...!");
        boardService.createBoard(boardCreateDto);

        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
    /**
     * [BoardController] 게시물 수정 함수
     * <pre>
     * @param id 게시물 고유번호를 입력합니다.
     * @param boardUpdateDto 게시판 업데이트에 필요한 게시판 정보를 담고 있는 객체입니다.
     * @return
     * true  : 업데이트된 게시물 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @PatchMapping("/post/{id}")
     @Operation(summary = "게시판 업데이트", description = "특정 게시판를 업데이트합니다.")
    public ResponseEntity<?> updateById(
            @PathVariable("id") String id,
            @RequestBody BoardEntity.UpdateDto boardUpdateDto) {

        log.info("[BoardController] updateBoard...!");

        boardService.updateById(id, boardUpdateDto);

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(boardService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }
    /**
     * [BoardController] 특정 게시판 삭제 함수
     *
     * @param id 게시판 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 게시판를 삭제처리합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @DeleteMapping("{id}")
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {

		log.info("[BoardController] deleteBoardPost...!");

        boardService.deleteById(id);

        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [BoardController] 특정 역할 조회 함수
     *
     * @param id 게시판 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 게시판 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @GetMapping("{id}")
    @Operation(summary = "ID로 게시판 찾기", description = "게시판를 조회합니다.")
    public ResponseEntity<?> findByBoardId(@PathVariable("id") String id) {

        log.info("[BoardController] findByBoardId...!");

        //        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.findById(id));
        return new ResponseEntity<>(boardService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

}
