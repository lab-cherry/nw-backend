package lab.cherry.nw.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lab.cherry.nw.model.QsheetEntity.ItemData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * ClassName : QsheetLogEntity
 * Type : class
 * Description : 큐시트 로그와 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : QsheetLogRepository, QsheetLogServiceImpl
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "qsheet_log")
@JsonPropertyOrder({ "id", "method"})
public class QsheetLogEntity implements Serializable {

    @Id
    @JsonProperty("qsheetLogSeq")
    @Schema(title = "큐시트 고유번호",type="String", example = "38352658567418867") // (Long) Tsid
    private String id;
    
    @JsonProperty("method")
    @Schema(title = "method", example = "create")
    private String method;

    @JsonProperty("qsheet")
    @Schema(title = "큐시트") // (Long) Tsid
    private QsheetEntity qsheet;

    //////////////////////////////////////////////////////////////////////////

}
