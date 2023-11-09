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
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * ClassName : FinaldocsEntity
 * Type : class
 * Description : Finaldocs와 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : FinaldocsRepository, FinaldocsServiceImpl
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "finaldocs")
@JsonPropertyOrder({ "id", "finaldocsName","finaltempl","user", "org","content","updated_at","created_at" })
public class FinaldocsEntity implements Serializable {

    @Id
    @JsonProperty("finaldocsSeq")
    @Schema(title = "최종확인서 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private String id;

	@NotNull
    @JsonProperty("finaltempl")
    @Schema(title = "최종확인서 템플릿 정보", example = "64ed89aa9e813b5ab16da6de")
    private FinalTemplEntity finaltempl;

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

	@NotNull
    @JsonProperty("finaldocsName")
	@Schema(title = "최종확인서 이름", example = "11시 그랜드 최종 확인서")
	@Size(min = 4, max = 255, message = "Minimum name length: 4 characters")
    private String name;

    @JsonProperty("content")
    @Schema(title = "최종확인서 내용", example = "")
    private List<Object> content;


    @JsonProperty("updated_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "최종확인서 수정 시간", example = "2023-07-04 12:00:00")
    private Instant updated_at;

    @JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "최종확인서 생성 시간", example = "2023-07-04 12:00:00")
    private Instant createdAt;


//////////////////////////////////////////////////////////////////////////

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class FinaldocsCreateDto {

		@NotBlank
        @Schema(title = "최종확인서 이름", example = "11시 그랜드 최종 확인서")
        @Size(min = 4, max = 255, message = "Minimum name length: 4 characters")
        private String name;

		@NotBlank
		@JsonProperty("user")
		@Schema(title = "사용자 정보", example = "64ed89aa9e813b5ab16da6de")
        private String user;

		@NotBlank
		@JsonProperty("org")
		@Schema(title = "조직 정보", example = "64ed89aa9e813b5ab16da6de")
        private String org;

		@NotBlank
		@JsonProperty("finaltempl")
		@Schema(title = "최종확인서 템플릿 정보", example = "64ed89aa9e813b5ab16da6de")
        private  String finaltempl;

//		@NotBlank
		@JsonProperty("content")
		@Schema(title = "최종확인서 내용", example = "")
        private List<Object> content;


    }


    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class FinaldocsUpdateDto {

		@Schema(title = "최종확인서 고유번호", example = "64ed89aa9e813b5ab16da6de")
        private String id;

		@Schema(title = "최종확인서 템플릿 정보", example = "64ed89aa9e813b5ab16da6de")
        private String finaltempl;

		@Schema(title = "사용자 정보", example = "64ed89aa9e813b5ab16da6de")
        private String user;

		@Schema(title = "조직 정보", example = "64ed89aa9e813b5ab16da6de")
        private String org;

		@Schema(title = "최종확인서 내용", example = "")
        private List<Object> content;


    }
}
