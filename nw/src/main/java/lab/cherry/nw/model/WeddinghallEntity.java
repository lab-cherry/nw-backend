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
 * ClassName : WeddinghallEntity
 * Type : class
 * Description : 웨딩홀(예식장)과 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : WeddinghallRepository
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "weddinghalls")
@JsonPropertyOrder({ "seq", "name", "maxPerson", "org", "interval", "image", "created_at", "updated_at" })
public class WeddinghallEntity implements Serializable {

    @Id
    @JsonProperty("seq")
    @Schema(title = "웨딩홀(예식장) 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private String id;

    @NotNull
    @JsonProperty("name")
    @Schema(title = "웨딩홀(예식장) 이름", example = "더글로리")
    private String name;

	@NotNull(message = "[필수] 웨딩홀(예식장 이름) 최대인원")
    @JsonProperty("maxPerson")
    @Schema(title = "웨딩홀(예식장) 최대인원", example = "100")
    @Range(min = 1, max = 300, message = "웨딩홀(예식장)의 최대인원은 1인 이상 300명 이하만 입력 가능합니다.")
    private Integer max_person;

    @DBRef
    @JsonProperty("org")
    @Schema(title = "Org 정보", example = "더모멘트")
//    private Set<OrgEntity> orgs = new HashSet<>();
    private OrgEntity org;
	
	@NotNull(message = "[필수] 웨딩홀(예식장) 행사 시간 간격")
    @JsonProperty("interval")
    @Schema(title = "웨딩홀(예식장) 행사 시간 간격", example = "30")
    @Range(min = 1, max = 300, message = "웨딩홀(예식장)의 행사 시간 간격은 1분 이상 300분 이하만 입력 가능합니다.")
    private Integer interval;
    
	@JsonProperty("images")
    @Schema(title = "웨딩홀(예식장) 이미지")
    private List<String> images;

    @JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "워딩홀(예식장) 생성 시간", example = "2023-07-04 12:00:00")
    private Instant created_at;

    @JsonProperty("updated_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "웨딩홀(예식장) 수정 시간", example = "2023-07-04 12:00:00")
    private Instant updated_at;

	//////////////////////////////////////////////////////////////////////////

	@Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class 	CreateDto {

		@NotBlank(message = "[필수] 웨딩홀(예식장 이름)")
        @Schema(title = "웨딩홀(예식장) 이름", example = "더글로리")
        @Size(min = 4, max = 10, message = "웨딩홀(예식장) 이름은 2글자 이상 10글자 이하만 입력 가능합니다.")
        private String name;

		@NotNull(message = "[필수] 웨딩홀(예식장 이름) 최대인원")
		@Schema(title = "웨딩홀(예식장) 최대인원", example = "100")
		@Range(min = 1, max = 300, message = "웨딩홀(예식장)의 최대인원은 1인 이상 300명 이하만 입력 가능합니다.")
		private Integer maxPerson;

		@NotNull(message = "[필수] Org 정보")
		@Schema(title = "Org 정보", example = "더모멘트")
		private String org;

		@NotNull(message = "[필수] 웨딩홀(예식장) 행사 시간 간격")
		@Schema(title = "웨딩홀(예식장) 행사 시간 간격", example = "30")
		@Range(min = 1, max = 300, message = "웨딩홀(예식장)의 행사 시간 간격은 1분 이상 300분 이하만 입력 가능합니다.")
		private Integer interval;
		
	}
}
