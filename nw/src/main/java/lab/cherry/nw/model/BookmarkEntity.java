package lab.cherry.nw.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * <pre>
 * ClassName : BookmarkEntity
 * Type : class
 * Description : 북마크와 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : BookmarkRepository, BookmarkServiceImpl
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "bookmark")
@JsonPropertyOrder({ "id", "userid"})
public class BookmarkEntity implements Serializable {

    @Id
    @JsonProperty("bookmarkSeq")
    @Schema(title = "북마크 고유번호", example = "38352658567418867") // (Long) Tsid
    private String id;

    @DBRef
    @JsonProperty("user")
	@Schema(title = "유저 고유번호", example = "38352658567418867") // (Long) Tsid
    private UserEntity user;

    @JsonProperty("data")
    private Map<String, String> data;

    @JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "북마크 생성 시간", example = "2023-07-04 12:00:00")
    private Instant created_at;

    @JsonProperty("updated_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "북마크 업데이트 시간", example = "2023-07-04 12:00:00")
    private Instant updated_at;


    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class BookmarkCreateDto {
		@Schema(title = "유저 고유번호", example = "38352658567418867")
        private String userSeq;
		@Schema(title = "북마크 정보",implementation=Map.class, example="{\"로그인\": \"/login\"}")
        private Map<String, String> data;
	}

	@Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class BookmarkUpdateDto {
		@Schema(title = "북마크 정보",implementation=Map.class, example="{\"로그인\": \"/login\"}")
        private Map<String, String> data;
    }

	public void updateFromDto(BookmarkUpdateDto updateDto) {
        if (updateDto.getData() != null) {
            this.data = updateDto.getData();
            this.updated_at = Instant.now();
        }
    }
    

}