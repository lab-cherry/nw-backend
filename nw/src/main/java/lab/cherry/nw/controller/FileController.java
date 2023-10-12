package lab.cherry.nw.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.springframework.core.io.InputStreamResource;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lab.cherry.nw.error.ResultResponse;
import lab.cherry.nw.error.enums.SuccessCode;
import lab.cherry.nw.model.FileEntity;
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
     * [FileController] 특정 파일 조회 함수
     *
     * @param path 파일 path를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 파일 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @GetMapping("info")
    @Operation(summary = "Path로 조직 찾기", description = "조직을 조회합니다.")
    public ResponseEntity<?> findByFilePath(@RequestParam(required = true) String path) {

		log.info("[OrgController] findByFilePath...!");

		final ResultResponse response = ResultResponse.of(SuccessCode.OK, fileService.findByPath(path));
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
	}

	/**
     * [FileController] 특정 파일 다운로드 함수
     *
     * @return 특정 파일을 반환합니다.
     *
     * Author : taking(taking@duck.com)
     */
     @GetMapping("/download/{orgId}")
     @Operation(summary = "특정 파일 다운로드", description = "특정 파일을 다운로드합니다.")
     public ResponseEntity<?> downloadFile(
               @RequestParam(required = false) Boolean qsheet,
			@RequestParam(required = false) List<String> path,
			@PathVariable String orgId) {

          if(path.size() > 1) {

               ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
               try (ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream)) {
                    for (String data : path) {

                         String[] parts = data.split("/");
                         String fileName = parts[parts.length - 1];

                         byte[] objectData = fileService.downloadZip(orgId, data);

                         // Zip 아카이브에 객체 추가
                         ZipArchiveEntry zipEntry = new ZipArchiveEntry(fileName + ".zip");
                         zipOut.putNextEntry(zipEntry);
                         zipOut.write(objectData);
                         zipOut.closeEntry();

                    }
               } catch (IOException e) {
                    log.error("{}", e);
               }
          
               byte[] zipBytes = byteArrayOutputStream.toByteArray();
               
               HttpHeaders headers = new HttpHeaders();
               headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "download.zip");

               return new ResponseEntity<>(zipBytes, headers, HttpStatus.OK);

          } else {

               String objectName = path.get(0);

               if(chkExtension(path.get(0))) {

                    // MinioService를 사용하여 MinIO 서버로부터 파일을 가져옵니다.
                    InputStream fileInputStream = fileService.downloadFile(orgId, objectName);

                    // 문자열을 '/'로 분할
                    String[] parts = objectName.split("/");

                    // 마지막 요소 확인
                    String fileName = parts[parts.length - 1];

                    // 파일 다운로드를 위한 헤더 설정
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentDispositionFormData("attachment", fileName); // 다운로드할 때 파일 이름 지정
                    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

                    // 파일 스트림을 InputStreamResource로 래핑하여 응답합니다.
                    InputStreamResource resource = new InputStreamResource(fileInputStream);

                    return new ResponseEntity<>(resource, headers, HttpStatus.OK);

               } else {

                    // 파일 다운로드를 위한 헤더 설정
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentDispositionFormData("attachment", "download.zip"); // 다운로드할 때 파일 이름 지정
                    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

                    return new ResponseEntity<>(fileService.downloadZip(orgId, objectName), headers, HttpStatus.OK);

               }

          }

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
