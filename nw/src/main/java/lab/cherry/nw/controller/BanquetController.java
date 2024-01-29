package lab.cherry.nw.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
import lab.cherry.nw.model.BanquetEntity;
import lab.cherry.nw.service.BanquetService;
import lab.cherry.nw.util.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * ClassName : BanquetController
 * Type : class
 * Description : 연회장 목록 조회, 연회장 상세 조회, 연회장업데이트, 연회장 삭제, 연회장 찾기 등 연회장과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : BanquetRepository, BanquetService, BanquetServiceImpl
 * </pre>
 */

// MEMO : 재사용성을 위해 ServiceImpl에서만 비즈니스 로직을 사용하기로 함
//        Dto를 통해 알맞는 파라미터로 데이터 가공 후 사용하기로 함
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/banquet")
@Tag(name = "Banquet", description = "Banquet API Document")
public class BanquetController {
    
	private final BanquetService banquetService;
    
    /**
     * [BanquetController] 연회장 목록 함수
     *
     * @return 전체 연회장 목록을 반환합니다.
     *
     * Author : taking(taking@duck.com)
     */
    @GetMapping("")
    @Operation(summary = "연회장 목록", description = "연회장 목록을 조회합니다.")
    public ResponseEntity<?> findAllBanquets(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        log.info("retrieve all banquet controller...!");

        Pageable pageable = PageRequest.of(page, size, Sort.by(Common.getOrder(sort)));

        Page<BanquetEntity> userEntity;
        if(name == null) {
			userEntity = banquetService.getBanquets(pageable);
        } else {
			userEntity = banquetService.findPageByName(name, pageable);
        }

        //        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.getUsers());
        return new ResponseEntity<>(userEntity, new HttpHeaders(), HttpStatus.OK);
    }
    
    /**
     * [BanquetController] 연회장 생성 함수
     *
     * @param banquetCreateDto 생성에 필요한 연회장 정보를 담고 있는 객체입니다.
     * @return
     * <pre>
     * true  : 성공(200)을 반환합니다.
     * false : 에러(400)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    @Operation(summary = "연회장 생성", description = "연회장을 생성합니다.")
    @ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "연회장 생성이 완료되었습니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "입력 값이 잘못되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> createBanquet(@Valid @RequestPart BanquetEntity.BanquetCreateDto banquetCreateDto,
											   		  @RequestPart List<MultipartFile> images) {

		log.info("[BanquetController] createBanquet...!");

		BanquetEntity banquetEntity =  banquetService.createBanquet(banquetCreateDto, images);

		return new ResponseEntity<>(banquetEntity, new HttpHeaders(), HttpStatus.OK);
    }
	
	/**
     * [BanquetController] 특정 연회장 삭제 함수
     *
     * @param id 연회장 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 연회장을 삭제처리합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @DeleteMapping("{id}")
    @Operation(summary = "연회장 삭제", description = "연회장를 삭제합니다.")
    public ResponseEntity<?> deleteBanquet(@PathVariable("id") String id) {

		log.info("[BanquetController] deleteBanquet...!");

		banquetService.deleteById(id);

		final ResultResponse response = ResultResponse.of(SuccessCode.OK);
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
	}


    /**
     * [BanquetController] 특정 연회장 조회 함수
     *
     * @param id 연회장 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 연회장 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * How-to:
     *  /api/v1/banquet/{id}
     *
     * Author : taking(taking@duck.com)
     */
    @GetMapping("{id}")
    @Operation(summary = "연회장 찾기", description = "연회장를 조회합니다.")
    public ResponseEntity<?> findBanquet(
            @PathVariable("id") String id) {

        log.info("[BanquetController] findBanquet...!");

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.findById(id));
        return new ResponseEntity<>(banquetService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

}