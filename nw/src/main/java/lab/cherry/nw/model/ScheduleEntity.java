package lab.cherry.nw.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@JsonPropertyOrder({ "id", "scheduleSeq", "org", "scheduleContent", "column", "created_at"})
public class ScheduleEntity implements Serializable {

    @Id
    @JsonProperty("scheduleSeq")
    @Schema(title = "스케줄표 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private String id;

    @DBRef
    @JsonProperty("org")
    @Schema(title = "조직 정보", example = "64ed89aa9e813b5ab16da6de")
    private OrgEntity org;

	  @JsonProperty("scheduleContent")
	  @Schema(title = "스케줄표 내용", example = "[]")
	  private List<Object> content;

    @JsonProperty("column")
    @Schema(title = "스케줄표  컬럼", example = "")
	  private Map<String, Object> column;

		@JsonProperty("created_at")
		@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    private Instant created_at;

		//////////////////////////////////////////////////////////////////////////

  @Getter
  @Builder
  @NoArgsConstructor @AllArgsConstructor
  public static class ScheduleCreateDto {

		@NotBlank
		@Schema(title = "조직 고유번호", example = "64ed89aa9e813b5ab16da6de")
		private String orgId;

		@Schema(title = "스케줄표 템플릿 내용", example = "문서 내용")
		private List<Object> content;

			}


	//////////////////////////////////////////////////////////////////////////

		@Getter
		@Setter
		@Builder
		@NoArgsConstructor @AllArgsConstructor
		public static class transDto {

			@Schema(title = "스케줄표 고유번호", example = "64ed89aa9e813b5ab16da6de")
			private  String finalTemplId;

			@Schema(title = "조직 고유번호", example = "64ed89aa9e813b5ab16da6de")
			private String orgId;
			
			@JsonProperty("scheduleContent")
			@Schema(title = "스케줄표 내용", example = "[]")
			private List<Object> content;

		}

}