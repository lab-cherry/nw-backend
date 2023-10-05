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
import lab.cherry.nw.model.BookmarkEntity;
import lab.cherry.nw.service.BookmarkService;
import lab.cherry.nw.util.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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
 * ClassName : BookmarkController
 * Type : class
 * Description : 북마크 목록 조회, 북마크 상세 조회, 북마크 업데이트, 북마크 삭제, 북마크 찾기 등 북마크과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : BookmarkRepository, BookmarkService, BookmarkServiceImpl
 * </pre>
 */

// MEMO : 재사용성을 위해 ServiceImpl에서만 비즈니스 로직을 사용하기로 함
//        Dto를 통해 알맞는 파라미터로 데이터 가공 후 사용하기로 함
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmark")
@Tag(name = "Bookmark", description = "Bookmark API Document")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    /**
     * [BookmarkController] 전체 북마크 목록 함수
     *
     * @return 전체 북마크 목록을 반환합니다.
     *
     * Author : yby654(yby654@github.com)
     */
    @GetMapping("")
    @Operation(summary = "북마크 목록", description = "북마크 목록을 조회합니다.")
    public ResponseEntity<?> findAllBookmarks(
            @RequestParam(required = false) String userid,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

    log.info("retrieve all bookmarks controller...!");

    Pageable pageable = PageRequest.of(page, size, Sort.by(Common.getOrder(sort)));

    Page<BookmarkEntity> bookmarkEntity;
    if(userid == null) {
        bookmarkEntity = bookmarkService.getBookmarks(pageable);
    } else  {
        bookmarkEntity = bookmarkService.findPageByUserId(userid, pageable);
    }
        return new ResponseEntity<>(bookmarkEntity, new HttpHeaders(), HttpStatus.OK);
	}

    /**
     * [BookmarkController] 북마크 생성 함수
     * <pre>
     * @param bookmarkCreateDto 북마크 생성에 필요한 북마크 정보를 담고 있는 객체입니다.
     * @return
     * true  : 업데이트된 북마크 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @PostMapping("")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "북마크 생성이 완료되었습니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "입력 값이 잘못 되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Operation(summary = "Bookmark 생성", description = "Bookmark를 추가합니다.")
    public ResponseEntity<?> createBookmark(@Valid @RequestBody BookmarkEntity.BookmarkCreateDto bookmarkCreateDto) {
        log.info("[BookmarkController] createBookmark...!");
        bookmarkService.createBookmark(bookmarkCreateDto);

        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
    /**
     * [BookmarkController] 북마크 수정 함수
     * <pre>
     * @param id 북마크 고유번호를 입력합니다.
     * @param bookmarkUpdateDto 북마크 업데이트에 필요한 북마크 정보를 담고 있는 객체입니다.
     * @return
     * true  : 업데이트된 큐시 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @PatchMapping("/user/{id}")
     @Operation(summary = "북마크 업데이트", description = "특정 북마크를 업데이트합니다.")
    public ResponseEntity<?> updateById(
            @PathVariable("id") String id,
			@RequestBody BookmarkEntity.BookmarkUpdateDto bookmarkUpdateDto) {

        log.info("[BookmarkController] updateBookmark...!");

        bookmarkService.updateById(id, bookmarkUpdateDto);

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(bookmarkService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }
//	/**
//     * [BookmarkController] 북마크 수정 함수
//     * <pre>
//     * @param userid 유저 고유번호를 입력합니다.
//     * @param bookmarkUpdateDto 북마크 업데이트에 필요한 북마크 정보를 담고 있는 객체입니다.
//     * @return
//     * true  : 업데이트된 큐시 정보를 반환합니다.
//     * false : 에러(400, 404)를 반환합니다.
//     * </pre>
//     *
//     * Author : yby654(yby654@github.com)
//     */
//	@PatchMapping("/user/{userid}")
//    @Operation(summary = "북마크 업데이트", description = "특정 북마크를 업데이트합니다.")
//    public ResponseEntity<?> updateByUserId(
//			@PathVariable("userid") String userid,
//			@RequestBody BookmarkEntity.UpdateDto bookmarkUpdateDto) {
//
//		log.info("[BookmarkController] updateBookmark...!");
//		ObjectId objectId = new ObjectId(userid);
//		bookmarkService.updateByUserId(userid, bookmarkUpdateDto);
//
//		//        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
//		return new ResponseEntity<>(bookmarkService.findByUserId(objectId), new HttpHeaders(), HttpStatus.OK);
//	}
    /**
     * [BookmarkController] 특정 북마크 삭제 함수
     *
     * @param id 북마크 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 북마크를 삭제처리합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @DeleteMapping("{id}")
    @Operation(summary = "북마크 삭제", description = "북마크를 삭제합니다.")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {

        log.info("[UserController] deleteUser...!");

        bookmarkService.deleteById(id);

        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [BookmarkController] 특정 역할 조회 함수
     *
     * @param id 북마크 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 북마크 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @GetMapping("/user/{id}")
    @Operation(summary = "ID로 북마크 찾기", description = "북마크를 조회합니다.")
    public ResponseEntity<?> findByBookmarkId(@PathVariable("id") String id) {

        log.info("[BookmarkController] findByBookmarkId...!");

        //        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.findById(id));
        return new ResponseEntity<>(bookmarkService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }
        
//    @GetMapping("/user/{userid}")
//    @Operation(summary = "User ID로 북마크 찾기", description = "북마크를 조회합니다.")
//    public ResponseEntity<?> findByBookmarkUserId(@PathVariable("userid") String userid) {
//
//        log.info("[BookmarkController] findByBookmarkUserId...!");
//        ObjectId objectId = new ObjectId(userid);
//            //        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.findById(id));
//            return new ResponseEntity<>(bookmarkService.findByUserId(objectId), new HttpHeaders(), HttpStatus.OK);
//    }

}
