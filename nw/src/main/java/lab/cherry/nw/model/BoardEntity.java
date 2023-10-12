package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * ClassName : BoardEntity
 * Type : class
 * Description : 게시판과 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : BoardRepository, BoardServiceImpl
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "board")
@JsonPropertyOrder({ "created_at"})
public class BoardEntity implements Serializable {

    @Id
    @JsonProperty("boardSeq")
    @Schema(title = "게시판 고유번호", example = "38352658567418867") // (Long) Tsid
    private String id;

    @DBRef
    @JsonProperty("userSeq")
    @Schema(title = "유저 고유번호", example = "38352658567418867") // (Long) Tsid
    private UserEntity userid;

    @JsonProperty("content")
    @Schema(title = "내용", example = "드디어 결혼합니다!")
//    @Size(min = 4, max = 255, message = "Minimum name length: 4 characters")
    private String content;

	@DBRef
    @JsonProperty("tags")
    @Schema(title = "태그", example = "스드메")
    private List<TagEntity> tag;
	
	@DBRef
    @JsonProperty("qsheet")
    @Schema(title = "큐시트", example = "큐시트") 
    private QsheetEntity qsheet;
	
	
    @JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "게시판 생성 시간", example = "2023-07-04 12:00:00")
    private Instant created_at;

    @JsonProperty("updated_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "게시판 업데이트 시간", example = "2023-07-04 12:00:00")
    private Instant updated_at;



    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class BoardCreateDto {

		@Schema(title = "유저 고유번호", example = "64f82e492948d933edfaa9c0")
        private String userSeq;
		@Schema(title = "게시물 내용", example = "안녕하세요~ 반갑습니다")
		private String content;
		@Schema(title = "큐시트 고유번호", example = "64f82e492948d933edfaa9c0")
		private String qsheetSeq;
		@Schema(title = "태그 목록")
		private List<String> tagList;
    }
//
    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class BoardUpdateDto {
		@Schema(title = "게시물 내용", example = "안녕하세요~ 반갑습니다")
		private String content;
		@Schema(title = "큐시트 고유번호", example = "64f82e492948d933edfaa9c0")
		private String qsheetSeq;
		@Schema(title = "태그 목록")
		private List<String> tagList;
    }

}
