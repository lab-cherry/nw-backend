package lab.cherry.nw.model;

import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * ClassName : DocsEntity
 * Type : class
 * Description : 문서 관리와 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : DocsRepository, DocsServiceImpl
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "docs")
@JsonPropertyOrder({ "id", "user", "org", "data", "created_at", "updated_at" })
public class DocsEntity implements Serializable {

    @Id
    @JsonProperty("docsSeq")
    @Schema(title = "문서 고유번호",type="String", example = "38352658567418867") // (Long) ObjectId
    private String id;

    @DBRef
    @JsonProperty("user")
    @Schema(title = "유저 정보") // (Long) ObjectId
    private UserEntity user;

    @DBRef
    @JsonProperty("org")
    @Schema(title = "조직 정보") // (Long) ObjectId
    private OrgEntity org;

    @JsonProperty("data")
	@Schema(title = "문서 내용")
    private DocsData data;

    @JsonProperty("created_at")
    // @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @JsonFormat(locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "큐시트 생성 시간", example = "2023-07-04 12:00:00")
    private Instant created_at;

    @JsonProperty("updated_at")
    // @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @JsonFormat(locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "큐시트 업데이트 시간", example = "2023-07-04 12:00:00")
    private Instant updated_at;

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class DocsData {

		@Schema(title = "문서 제목", example = "선언문")
        private String title;

		@Schema(title = "문서 작성자", example = "더모멘트")
        private String author;

		@Schema(title = "문서 내용", example = "문서 내용입니다.")
        private String content;

    }
    
    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class DocsCreateDto {

		@NotNull
		@Schema(title = "유저 고유번호", example = "38352658567418867")
        private String userSeq;

		@Schema(title = "조직 고유번호", example = "38352658567418867")
        private String orgSeq;

		@Schema(title = "데이터 리스트")
		private DocsData data;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class DocsUpdateDto {

		@Schema(title = "데이터 리스트")
		private DocsData data;

    }
}