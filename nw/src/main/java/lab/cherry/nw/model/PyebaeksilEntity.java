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

/**
 * <pre>
 * ClassName : PyebaeksilEntity
 * Type : class
 * Description : 폐백실과 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : PyebaeksilRepository
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "pyebaeksils")
@JsonPropertyOrder({ "pyebaeksilSeq", "pyebaeksilName", "org", "image", "created_at", "updated_at" })
public class PyebaeksilEntity implements Serializable {

    @Id
    @JsonProperty("pyebaeksilSeq")
    @Schema(title = "폐백실 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private String id;

    @NotNull
    @JsonProperty("pyebaeksilName")
    @Schema(title = "폐백실 이름", example = "더글로리")
    private String name;

    @DBRef
    @JsonProperty("org")
    @Schema(title = "Org 정보", example = "더모멘트")
    private OrgEntity org;
    
	@JsonProperty("images")
    @Schema(title = "폐백실 이미지")
    private List<String> images;

    @JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "폐백실 생성 시간", example = "2023-07-04 12:00:00")
    private Instant created_at;

    @JsonProperty("updated_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "폐백실 수정 시간", example = "2023-07-04 12:00:00")
    private Instant updated_at;

	//////////////////////////////////////////////////////////////////////////

	@Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class PyebaeksilCreateDto {

		@NotBlank(message = "[필수] 폐백실")
        @Schema(title = "폐백실 이름", example = "더글로리")
        @Size(min = 4, max = 10, message = "폐백실 이름은 2글자 이상 10글자 이하만 입력 가능합니다.")
        private String pyebaeksilName;
		
		@NotNull(message = "[필수] Org 정보")
		@Schema(title = "Org 정보", example = "더모멘트")
		private String org;
		
	}
}
