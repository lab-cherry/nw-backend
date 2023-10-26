package lab.cherry.nw.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * <pre>
 * ClassName : TagEntity
 * Type : class
 * Description : 태그와 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : TagRepository, TagServiceImpl
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "tag")
@JsonPropertyOrder({ "created_at"})
public class TagEntity {
	@Id
    @JsonProperty("tagSeq")
    @Schema(title = "태그 고유번호", example = "38352658567418867") // (Long) Tsid
    private String id;
	
	@JsonProperty("content")
    @Schema(title = "이름", example = "스드메")
	@Size(min = 2, max = 10, message = "Minimum name length: 2 characters")
	private String name;
	
	@Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class TagCreateDto {

		@Schema(title = "이름", example = "스드메")
		@Size(min = 2, max = 10, message = "Minimum name length: 2 characters")
        private String name;
	}
	
}