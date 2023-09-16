package lab.cherry.nw.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lab.cherry.nw.model.FileEntity;
import lab.cherry.nw.service.FileService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * ClassName : FileController
 * Type : class
 * Description : 파일 목록 조회, 파일 삭제, 파일 찾기 등 파일과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : FileRepository, FileService, FileServiceImpl
 * </pre>
 */

// MEMO : 재사용성을 위해 ServiceImpl에서만 비즈니스 로직을 사용하기로 함
//        Dto를 통해 알맞는 파라미터로 데이터 가공 후 사용하기로 함
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
@Tag(name = "File", description = "File API Document")
public class FileController {

    private final FileService fileService;

    /**
     * [FileController] 전체 파일 목록 함수
     *
     * @return 전체 파일 목록을 반환합니다.
     *
     * Author : taking(taking@duck.com)
     */
    @GetMapping("")
    @Operation(summary = "파일 목록", description = "파일 목록을 조회합니다.")
    public ResponseEntity<?> findAllFiles(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        log.info("retrieve all files controller...!");

        Pageable pageable = PageRequest.of(page, size, Sort.by(Common.getOrder(sort)));

        Page<FileEntity> fileEntity;
        if(name == null) {
			fileEntity = fileService.getFiles(pageable);
        } else {
			fileEntity = fileService.findPageByName(name, pageable);
        }

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.getUsers());
		return new ResponseEntity<>(fileEntity, new HttpHeaders(), HttpStatus.OK);
    }
}
