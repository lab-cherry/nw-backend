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
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * <pre>
 * ClassName : BanquetEntity
 * Type : class
 * Description : 연회장과 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : BanquetRepository
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "banquets")
@JsonPropertyOrder({ "seq", "name", "maxPerson", "org", "interval", "image", "created_at", "updated_at" })
public class BanquetEntity implements Serializable {

    @Id
    @JsonProperty("seq")
    @Schema(title = "연회장 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private String id;

    @NotNull
    @JsonProperty("name")
    @Schema(title = "연회장 이름", example = "더글로리")
    private String name;

	@NotNull(message = "[필수] 연회장 최대인원")
    @JsonProperty("maxPerson")
    @Schema(title = "연회장 최대인원", example = "100")
    @Range(min = 1, max = 300, message = "연회장의 최대인원은 1인 이상 300명 이하만 입력 가능합니다.")
    private Integer max_person;

    @DBRef
    @JsonProperty("org")
    @Schema(title = "Org 정보", example = "더모멘트")
    private OrgEntity org;
	
	@NotNull(message = "[필수] 연회장 행사 시간 간격")
    @JsonProperty("interval")
    @Schema(title = "연회장 행사 시간 간격", example = "30")
    @Range(min = 1, max = 300, message = "연회장의 행사 시간 간격은 1분 이상 300분 이하만 입력 가능합니다.")
    private Integer interval;
    
	@JsonProperty("images")
    @Schema(title = "연회장 이미지")
    private List<String> images;

    @JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "연회장 생성 시간", example = "2023-07-04 12:00:00")
    private Instant created_at;

    @JsonProperty("updated_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "연회장 수정 시간", example = "2023-07-04 12:00:00")
    private Instant updated_at;

	//////////////////////////////////////////////////////////////////////////

	@Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class BanquetCreateDto {

		@NotBlank(message = "[필수] 연회장")
        @Schema(title = "연회장 이름", example = "더글로리")
        @Size(min = 4, max = 10, message = "연회장 이름은 2글자 이상 10글자 이하만 입력 가능합니다.")
        private String name;

		@NotNull(message = "[필수] 연회장 최대인원")
		@Schema(title = "연회장 최대인원", example = "100")
		@Range(min = 1, max = 300, message = "연회장의 최대인원은 1인 이상 300명 이하만 입력 가능합니다.")
		private Integer maxPerson;

		@NotNull(message = "[필수] Org 정보")
		@Schema(title = "Org 정보", example = "더모멘트")
		private String org;

		@NotNull(message = "[필수] 연회장 행사 시간 간격")
		@Schema(title = "연회장 행사 시간 간격", example = "30")
		@Range(min = 1, max = 300, message = "연회장의 행사 시간 간격은 1분 이상 300분 이하만 입력 가능합니다.")
		private Integer interval;
		
	}
}
