package lab.cherry.nw.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import lab.cherry.nw.model.TagEntity;
import lab.cherry.nw.service.TagService;
import lab.cherry.nw.util.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * ClassName : TagController
 * Type : class
 * Description : 태그 목록 조회, 태그 상세 조회, 태그 업데이트, 태그 삭제, 태그 찾기 등 태그과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : TagRepository, TagService, TagServiceImpl
 * </pre>그
 */

// MEMO : 재사용성을 위해 ServiceImpl에서만 비즈니스 로직을 사용하기로 함
//        Dto를 통해 알맞는 파라미터로 데이터 가공 후 사용하기로 함
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board/tag")
@Tag(name = "Tag", description = "Tag API Document")
public class TagController {
    private final TagService tagService;

	/**
     * [TagController] 전체 태그 목록 함수
     *
     * @return 전체 태그 목록을 반환합니다.
     *그
     * Author : yby654(yby654@github.com)
     */
    @GetMapping("")
    @Operation(summary = "태그 목록", description = "태그 목록을 조회합니다.")
    public ResponseEntity<?> findAllTags(
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "5") Integer size,
			@RequestParam(defaultValue = "id,desc") String[] sort) {

		log.info("retrieve all tags controller...!");

		Pageable pageable = PageRequest.of(page, size, Sort.by(Common.getOrder(sort)));

		Page<TagEntity> tagEntity;
		tagEntity = tagService.getTags(pageable);
		return new ResponseEntity<>(tagEntity, new HttpHeaders(), HttpStatus.OK);
	}
	
   

    /**
     * [TagController] 태그 생성 함수
     * <pre>
     * @param tagCreateDto 태그 생성에 필요한 태그 정보를 담고 있는 객체입니다.
     * @return
     * true  : 업데이트된 태그 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @PostMapping("")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "태그 생성이 완료되었습니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "입력 값이 잘못 되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Operation(summary = "Tag 생성", description = "Tag를 추가합니다.")
    public ResponseEntity<?> createTag(@Valid @RequestBody TagEntity.TagCreateDto tagCreateDto) {
        log.info("[TagController] createTag...!");
        tagService.createTag(tagCreateDto);

        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
  

    /**
     * [TagController] 특정 태그 조회 함수
     *
     * @param name 태그 이름 입력합니다.
     * @return
     * <pre>
     * true  : 특정 태그 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @GetMapping("/{name}")
    @Operation(summary = "ID로 태그 찾기", description = "태그를 조회합니다.")
    public ResponseEntity<?> findByTagId(@PathVariable("name") String name) {

        log.info("[TagController] findByTagId...!");

        //        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.findById(id));
		return new ResponseEntity<>(tagService.findByName(name), new HttpHeaders(), HttpStatus.OK);
    }
        
//    @GetMapping("/user/{userid}")
//    @Operation(summary = "User ID로 태그 찾기", description = "태그를 조회합니다.")
//    public ResponseEntity<?> findByTagUserId(@PathVariable("userid") String userid) {
//
//        log.info("[TagController] findByTagUserId...!");
//        ObjectId objectId = new ObjectId(userid);
//            //        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.findById(id));
//            return new ResponseEntity<>(tagService.findByUserId(objectId), new HttpHeaders(), HttpStatus.OK);
//    }

}
