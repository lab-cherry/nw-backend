package lab.cherry.nw.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@JsonPropertyOrder({ "id", "scheduleSeq","userid","orgid","finaltemplid","scheduleName", "scheduleContent", "column"})
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

	@JsonProperty("scheduleContent")
	@Schema(title = "스케줄표 내용", example = "[]")
	private List<FinaldocsEntity> content;

    @JsonProperty("column")
    @Schema(title = "스케줄표  컬럼", example = "")
	private Map<String, String> column;

//////////////////////////////////////////////////////////////////////////

	@Getter
	@Setter
	@Builder
	@NoArgsConstructor @AllArgsConstructor
	public static class transDto {

		@Schema(title = "사용자 고유번호", example = "64ed89aa9e813b5ab16da6de")
		private String userid;

		@Schema(title = "스케줄표 고유번호", example = "64ed89aa9e813b5ab16da6de")
		private  String finalTemplid;

		@Schema(title = "조직 고유번호", example = "64ed89aa9e813b5ab16da6de")
		private String orgid;

		@Schema(title = "스케줄표 이름", example = "문서1")
		@Size(min = 4, max = 255, message = "Minimum name length: 4 characters")
		private String name;

		@JsonProperty("scheduleContent")
		@Schema(title = "스케줄표 내용", example = "[]")
		private String content;

	}

}