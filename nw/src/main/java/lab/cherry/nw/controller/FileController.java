package lab.cherry.nw.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
import lab.cherry.nw.model.FileEntity;
import lab.cherry.nw.model.WeddinghallEntity;
import lab.cherry.nw.service.FileService;
import lab.cherry.nw.util.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
     * [OrgController] 전체 조직 목록 함수
     *
     * @return 전체 조직 목록을 반환합니다.
     *
     * Author : taking(taking@duck.com)
     */
    @GetMapping("")
    @Operation(summary = "파일 목록", description = "파일 목록을 조회합니다.")
    public ResponseEntity<?> findAllFiles(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String path,
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "5") Integer size,
			@RequestParam(defaultValue = "id,desc") String[] sort) {

		log.info("retrieve all orgs controller...!");

		Pageable pageable = PageRequest.of(page, size, Sort.by(Common.getOrder(sort)));

		Page<FileEntity> fileEntity = fileService.getFiles(pageable);
		return new ResponseEntity<>(fileEntity, new HttpHeaders(), HttpStatus.OK);
	}

	/**
     * [FileController] 특정 파일 조회 함수
     *
     * @param id 파일 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 파일의 정보를 반홥합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @GetMapping("{id}")
    @Operation(summary = "파일 조회", description = "파일을 조회합니다.")
    public ResponseEntity<?> findByFileId(@PathVariable("id") String id) {
		log.info("[FileController] findByFileId...!");

//		final ResultResponse response = ResultResponse.of(SuccessCode.OK);
		return new ResponseEntity<>(fileService.findById(id), new HttpHeaders(), HttpStatus.OK);
	}

    
    /**
     * [FileController] 파일 업로드 함수
     *
     * @param files 파일 업로드 객체 리스트입니다.
     * @return
     * <pre>
     * true  : 성공(200)을 반환합니다.
     * false : 에러(400)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @PostMapping(value = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    @Operation(summary = "파일 업로드", description = "파일을 업로드합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 업로드가 완료되었습니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "입력 값이 잘못되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> uploadFile(@RequestPart List<MultipartFile> files) {

		log.info("[FileController] uploadFile...!");
          
        ResultResponse result = ResultResponse.of(SuccessCode.FILE_UPLOAD_SUCCESS, fileService.uploadFiles(new ObjectId().toString(), files));
        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
    }
	     

	/**
     * [FileController] 특정 파일 다운로드 함수
     *
     * @return 특정 파일을 반환합니다.
     *
     * Author : taking(taking@duck.com)
	 * @throws IOException
	 * @throws IllegalStateException
     */
     @GetMapping("/download/{file_id}")
     @Operation(summary = "특정 파일 다운로드", description = "특정 파일을 다운로드합니다.")
     public ResponseEntity<?> downloadFile(@PathVariable String file_id) throws IllegalStateException, IOException {

          FileEntity.LoadFile file = fileService.downloadFile(file_id);

          String fileName = null;
          try {
               fileName = URLEncoder.encode(file.getName(), "UTF-8");
          } catch (UnsupportedEncodingException e) {
               log.error("{}", e);
          }
          
          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.parseMediaType(file.getType()));
          headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

          return new ResponseEntity<>(new ByteArrayResource(file.getFile()), headers, HttpStatus.OK);
	}

     @GetMapping("/downloads/{id}")
     @Operation(summary = "특정 파일 그룹 다운로드", description = "특정 그룹의 파일을 일괄 다운로드합니다. (ex. 웨딩홀 Seq, 연회장 Seq, 큐시트 Seq 등)")
     public ResponseEntity<?> downloadFiles(@PathVariable String id) {

          Map<String, Object> val = fileService.downloadFiles("seq", id);

          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.parseMediaType("application/zip"));
          headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + val.get("name") + "\"");

          return new ResponseEntity<>(val.get("data"), headers, HttpStatus.OK);
	}

	/**
     * [FileController] 특정 파일 삭제 함수
     *
     * @param id 파일 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 파일 삭제처리합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @DeleteMapping("{id}")
    @Operation(summary = "파일 삭제", description = "파일을 삭제합니다.")
    public ResponseEntity<?> deleteFile(@PathVariable("id") String id) {

		log.info("[FileController] deleteFile...!");

		fileService.deleteById(id);

		final ResultResponse response = ResultResponse.of(SuccessCode.OK);
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
	}

     public Boolean chkExtension(String path) {
          String extension = StringUtils.getFilenameExtension(path);

          if (extension == null) {
               return false;
          } else {
               return true;
          }
     }
}
