package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

/**
 * <pre>
 * ClassName : ScheduleEntity
 * Type : class
 * Description : 스케줄표와 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : FinalTemplRepository, FinalTemplServiceImpl
 * </pre>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "schedule")
@JsonPropertyOrder({ "id", "scheduleSeq","userid","orgid","finaltemplid","scheduleName", "content"})
public class ScheduleEntity implements Serializable {

    @Id
    @JsonProperty("scheduleSeq")
    @Schema(title = "스케줄표 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private String id;


    @DBRef
    @JsonProperty("userid")
    @Schema(title = "사용자 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private UserEntity userid;

    @DBRef
    @JsonProperty("orgid")
    @Schema(title = "조직 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private OrgEntity orgid;

	@JsonProperty("finaltemplid")
	@Schema(title = "스케줄표 고유번호", example = "64ed89aa9e813b5ab16da6de")
	private FinalTemplEntity finaltemplid;


    @JsonProperty("scheduleName")
    @Schema(title = "스케줄표 이름", example = "문서1")
    @Size(min = 4, max = 255, message = "Minimum name length: 4 characters")
    private String name;

    @JsonProperty("content")
    @Schema(title = "스케줄표  컬럼", example = "")
//    private FinalTemplEntity column;
	private Map column;

//
//    @JsonProperty("updated_at")
//    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
//    @Schema(title = "스케줄표 수정 시간", example = "2023-07-04 12:00:00")
//    private Instant updated_at;
//
//    @JsonProperty("created_at")
//    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
//    @Schema(title = "스케줄표 템플릿 생성 시간", example = "2023-07-04 12:00:00")
//    private Instant created_at;


//////////////////////////////////////////////////////////////////////////

	@Getter
	@Setter
	@Builder
	@NoArgsConstructor @AllArgsConstructor
	public static class transDto {

		private String userid;

		private  String finalTemplid;

		private String orgid;

		private String name;

		private String content;


	}

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class CreateDto {

    private String name;

    private String userid;

    private String orgid;

    private Map content;

    }




}
