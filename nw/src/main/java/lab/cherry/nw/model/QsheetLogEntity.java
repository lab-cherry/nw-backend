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
 * ClassName : QsheetEntity
 * Type : class
 * Description : 조직과 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : QsheetRepository, QsheetServiceImpl
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

    // @JsonProperty("qsheetSeq")
    // @Schema(title = "큐시트 고유번호",type="String", example = "38352658567418867") // (Long) Tsid
    // private String qsheetId;

    // @DBRef
    // @JsonProperty("userSeq")
    // @Schema(title = "유저 고유번호", type="String",example = "38352658567418867") // (Long) Tsid
    // private UserEntity user;

    // @DBRef
    // @JsonProperty("orgSeq")
    // @Schema(title = "조직 정보", type="String",example = "38352658567418867") // (Long) Tsid
    // private OrgEntity orgid;


    // @JsonProperty("name")
    // @Schema(title = "큐시트 이름", type="String",example = "최해리_230824")
    // @Size(min = 4, max = 255, message = "Minimum name length: 4 characters")
    // private String name;

    // @JsonProperty("data")
	// @Schema(title = "큐시트 내용")
    // private List<QsheetEntity.ItemData> data;

    // @JsonProperty("created_at")
    // @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    // @Schema(title = "큐시트 생성 시간", example = "2023-07-04 12:00:00")
    // private Instant created_at;

    // @JsonProperty("updated_at")
    // @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    // @Schema(title = "큐시트 업데이트 시간", example = "2023-07-04 12:00:00")
    // private Instant updated_at;

    // @JsonProperty("memo")
    // @Schema(title = "메모", type="String", example = "신랑 깜짝 이벤트 준비")
    // private String memo;

    // @DBRef
    // @JsonProperty("org_approver")
    // @Schema(title = "업체 확인자 정보",type="String",example = "38352658567418867")
    // private UserEntity org_approver;
    // @JsonProperty("org_confirm")
    // @Schema(title = "업체 확인", type="Boolean", example = "false")
    // private boolean org_confirm;
    // @JsonProperty("client_confirm")
	// @Schema(title = "신랑신부 확인", type="Boolean", example = "false")
	// private boolean client_confirm;



    //    @Builder.Default
    //    @JsonProperty("users")
    //    @Schema(title = "조직 소속 사용자 정보", example = "user1, user2")
    //    @ManyToMany(fetch = FetchType.LAZY)
    //    private Set<UserEntity> users = new HashSet<>();

    //////////////////////////////////////////////////////////////////////////

}
