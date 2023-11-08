package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

/**
 * <pre>
 * ClassName : finaltemplEntity
 * Type : class
 * Description : 최종확인서 템플릿과 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : FinalTemplRepository, FinalTemplServiceImpl
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "finaltempl")
@JsonPropertyOrder({ "id", "content", "user", "org", "updated_at", "created_at"})
public class FinalTemplEntity implements Serializable {

    @Id
    @JsonProperty("finaltemplSeq")
    @Schema(title = "최종확인서 템플릿 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private String id;

	@NotNull
    @DBRef
    @JsonProperty("user")
    @Schema(title = "사용자 정보", example = "64ed89aa9e813b5ab16da6de")
    private UserEntity user;

	@NotNull
    @DBRef
    @JsonProperty("org")
    @Schema(title = "조직 정보", example = "64ed89aa9e813b5ab16da6de")
    private OrgEntity org;

    @JsonProperty("content")
    @Schema(title = "최종확인서 템플릿 내용", example = "문서 내용")
    private Map<String, Object> content;

    @JsonProperty("updated_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "최종확인서 템플릿 수정 시간", example = "2023-07-04 12:00:00")
    private Instant updated_at;

    @JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "최종확인서 템플릿 생성 시간", example = "2023-07-04 12:00:00")
    private Instant created_at;

//////////////////////////////////////////////////////////////////////////

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class FinalTemplCreateDto {

	@NotBlank
	@Schema(title = "사용자 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private String userSeq;

	@NotBlank
	@Schema(title = "조직 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private String orgId;

	@Schema(title = "최종확인서 템플릿 내용", example = "문서 내용")
    private Map<String, Object> content;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class FinalTemplUpdateDto {

		@Schema(title = "사용자 고유 번호", example = "64ed89aa9e813b5ab16da6de")
        private String userSeq;

		@Schema(title = "조직 고유 번호", example = "64ed89aa9e813b5ab16da6de")
        private String orgId;

		@Schema(title = "최종확인서 템플릿 내용", example = "문서 내용")
		private Map<String, Object> content;

    }

}
