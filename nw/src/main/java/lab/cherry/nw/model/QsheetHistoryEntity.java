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
import java.util.Map;


/**
 * <pre>
 * ClassName : QsheetHistoryEntity
 * Type : class
 * Description : 큐시트 히스토리와 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : QsheetHistoryRepository, QsheetHistoryServiceImpl
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "qsheetHistory")
@JsonPropertyOrder({ "id", "name"})
public class QsheetHistoryEntity implements Serializable {

    @Id
    @JsonProperty("qsheetHistorySeq")
    @Schema(title = "큐시트 히스토리 고유번호",type="String", example = "38352658567418867") // (Long) Tsid
    private String id;

    @DBRef
    @JsonProperty("qsheet")
    @Schema(title = "큐시트 고유번호",type="String", example = "38352658567418867") // (Long) Tsid
    private QsheetEntity qsheetid;

    @DBRef
    @JsonProperty("user")
    @Schema(title = "유저 고유번호", type="String",example = "38352658567418867") // (Long) Tsid
    private UserEntity userid;

    @JsonProperty("content")
    @Schema(title = "수정 내용", type="String",example = "큐시트 제목 변경")
    // @Size(min = 4, max = 255, message = "Minimum name length: 4 characters")
    private  List<Map<String,String>> content;

    @JsonProperty("updated_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "큐시트 업데이트 시간", example = "2023-07-04 12:00:00")
    private Instant updated_at;


}
