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
import java.util.List;
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
@JsonPropertyOrder({ "id", "scheduleSeq","user","org","finaltempl","scheduleName", "scheduleContent", "column"})
public class ScheduleEntity implements Serializable {

    @Id
    @JsonProperty("scheduleSeq")
    @Schema(title = "스케줄표 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private String id;

    @DBRef
    @JsonProperty("user")
    @Schema(title = "사용자 정보", example = "64ed89aa9e813b5ab16da6de")
    private UserEntity user;

    @DBRef
    @JsonProperty("org")
    @Schema(title = "조직 정보", example = "64ed89aa9e813b5ab16da6de")
    private OrgEntity org;

	  @JsonProperty("finaltemplid")
	  @Schema(title = "최종확인서 템플릿 정보", example = "64ed89aa9e813b5ab16da6de")
	  private FinalTemplEntity finaltempl;

    @JsonProperty("scheduleName")
    @Schema(title = "스케줄표 이름", example = "문서1")
    @Size(min = 4, max = 255, message = "Minimum name length: 4 characters")
    private String name;

	  @JsonProperty("scheduleContent")
	  @Schema(title = "스케줄표 내용", example = "[]")
	  private List<FinaldocsEntity> content;

    @JsonProperty("column")
    @Schema(title = "스케줄표  컬럼", example = "")
	  private Map<String,String> column;

//////////////////////////////////////////////////////////////////////////

	@Getter
	@Setter
	@Builder
	@NoArgsConstructor @AllArgsConstructor
	public static class transDto {

		@Schema(title = "사용자 정보", example = "64ed89aa9e813b5ab16da6de")
		private String user;

		@Schema(title = "스케줄표 정보", example = "64ed89aa9e813b5ab16da6de")
		private  String finalTempl;

		@Schema(title = "조직 정보", example = "64ed89aa9e813b5ab16da6de")
		private String org;

		@Schema(title = "스케줄표 이름", example = "문서1")
		@Size(min = 4, max = 255, message = "Minimum name length: 4 characters")
		private String name;

		@JsonProperty("scheduleContent")
		@Schema(title = "스케줄표 내용", example = "[]")
		private Map<String,String> content;

	}

}
