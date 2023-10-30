package lab.cherry.nw.model;

import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
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

/**
 * <pre>
 * ClassName : EventEntity
 * Type : class
 * Description : 캘린더에 표시되는 이벤트와 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : EventRepository
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "events")
@JsonPropertyOrder({ "eventSeq", "eventTitle", "eventLocation", "resDate", "weddingDate", "evenDate", "updated_at" })
public class EventEntity implements Serializable {

    @Id
    @JsonProperty("eventSeq")
    @Schema(title = "이벤트 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private String id;

	@NotNull
    @JsonProperty("eventTitle")
    @Schema(title = "이벤트 이름", example = "{time}시 {title}")
    @Size(min = 1, max = 20, message = "이벤트 이름은 20자 이내로만 가능합니다.")
    private String title;

    // @JsonProperty("eventDescription")
    // @Schema(title = "이벤트 설명", example = "오전 11시 더모멘트홀에서 원빈/이나영 커플의 행사가 있음")
    // @Range(min = 1, max = 20, message = "이벤트 이름은 20자 이내로만 가능합니다.")
    // private String description;

	@NotNull
    @JsonProperty("eventLocation")
    @Schema(title = "이벤트 장소", example = "더모멘트홀")
    @Size(min = 1, max = 20, message = "이벤트 장소명은 20글자 이내로만 가능합니다.")
    private String location;

    @NotBlank
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "예약 날짜", example = "2023-08-29T05:11:38.002Z")
    private String resDate;

    @NotBlank
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "예식 날짜", example = "2023-08-29T05:11:38.002Z")
    
    private String weddingDate;
    // @DBRef
    // @JsonProperty("usercard")
    // @Schema(title = "UserCard 정보", example = "UserCard")
    // private UserCardEntity usercard;

    @JsonProperty("updated_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "이벤트 마지막 업데이트 시간", example = "2023-07-04 12:00:00")
    private Instant updated_at;
    
    public void editTitle(String title) {
        this.title = title;
    }

}
