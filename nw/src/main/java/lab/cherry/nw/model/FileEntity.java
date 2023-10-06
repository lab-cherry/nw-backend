package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;

/**
 * <pre>
 * ClassName : FileEntity
 * Type : class
 * Description : 파일과 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : FileRepository, FileServiceImpl
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "files")
@JsonPropertyOrder({ "fileSeq", "fileName", "fileType", "fileExt", "filePath", "fileSize", "userId", "orgId", "created_at" })
public class FileEntity implements Serializable {

	@Id
    @JsonProperty("fileSeq")
    @Schema(title = "파일 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private String id;

	@NotNull
    @JsonProperty("fileName")
    @Schema(title = "파일 이름", example = "2023-09-05.jpg")
    private String name;

	@NotNull
    @JsonProperty("fileType")
    @Schema(title = "파일 타입", example = "image/jpeg")
    private String type;

	@NotNull
    @JsonProperty("filePath")
    @Schema(title = "파일 경로", example = "관리/더 글로리/IMG_61E29A079818-1.jpeg")
    private String path;

	@NotNull
    @JsonProperty("fileSize")
    @Schema(title = "파일 사이즈", example = "20MB")
    private String size;

    @JsonProperty("userId")
    @Schema(title = "사용자 아이디", example = "admin")
	private String userid;

	@NotNull
    @JsonProperty("orgId")
    @Schema(title = "조직 고유번호", example = "64ed89aa9e813b5ab16da6dd")
	private String orgid;

	@JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "조직 생성 시간", example = "2023-07-04 12:00:00")
    private Instant created_at;
	
}