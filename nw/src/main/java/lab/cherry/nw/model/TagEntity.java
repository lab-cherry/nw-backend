package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
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
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <pre>
 * ClassName : BoardEntity
 * Type : class
 * Description : 게시판과 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : BoardRepository, BoardServiceImpl
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "tag")
@JsonPropertyOrder({ "created_at"})
public class TagEntity {
	@Id
    @JsonProperty("boardSeq")
    @Schema(title = "게시판 고유번호", example = "38352658567418867") // (Long) Tsid
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