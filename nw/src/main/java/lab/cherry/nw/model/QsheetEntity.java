package lab.cherry.nw.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
@Document(collection = "qsheet")
@JsonPropertyOrder({ "id", "name"})
public class QsheetEntity implements Serializable {

    @Id
    @JsonProperty("qsheetSeq")
    @Schema(title = "큐시트 고유번호",type="String", example = "38352658567418867") // (Long) Tsid
    private String id;

    @DBRef
    @JsonProperty("user")
    @Schema(title = "유저 정보") // (Long) Tsid
    private UserEntity user;

    @DBRef
    @JsonProperty("org")
    @Schema(title = "조직 정보") // (Long) Tsid
    private OrgEntity org;

    @JsonProperty("name")
    @Schema(title = "큐시트 이름", type="String",example = "최해리_230824")
    private String name;

    @JsonProperty("data")
	@Schema(title = "큐시트 내용")
    private List<ItemData> data;

    @JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd a hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "큐시트 생성 시간", example = "2023-07-04 12:00:00")
    private Instant created_at;

    @JsonProperty("wedding_date")
    @JsonFormat(locale = "ko_KR", timezone = "Asia/Seoul")
    // @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "결혼식 날짜", example = "2023-07-04 12:00:00")
    private String wedding_date;

    @JsonProperty("updated_at")
    @JsonFormat(pattern="yyyy-MM-dd a hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "큐시트 업데이트 시간", example = "2023-07-04 12:00:00")
    private Instant updated_at;

    @JsonProperty("type")
    @Schema(title = "타입", type="String", example = "템플릿 or 유저")
    private String type;

    @JsonProperty("memo")
    @Schema(title = "메모", type="String", example = "신랑 깜짝 이벤트 준비")
    private String memo;

    @DBRef
    @JsonProperty("org_approver")
    @Schema(title = "업체 확인자 정보",type="String",example = "38352658567418867")
    private UserEntity org_approver;
    @JsonProperty("org_confirm")
    @Schema(title = "업체 확인", type="Boolean", example = "false")
    private boolean org_confirm;
    @JsonProperty("client_confirm")
	@Schema(title = "신랑신부 확인", type="Boolean", example = "false")
	private boolean client_confirm;
    // @JsonProperty("final_confirm")
    // @Schema(title = "최종 확인")
    // private FinalConfirm finalConfirm;
    

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class ItemData {
		@Schema(title = "큐시트 순서",type="integer" ,example = "1")
        private Integer orderIndex;
		@Schema(title = "큐시트 순서명", example = "축가")
		private String process;
		@Schema(title = "큐시트 내용", example = "니가사는그집-박진영")
        private String content;
		@Schema(title = "큐시트 행위자", example = "신부")
        private String actor;
		@Schema(title = "비고", example = "신부가 노래를 못함")
        private String note;
		@Schema(title = "파일 이름", example = "[축가.mp3, 축하영상.mp4]")
        private List<String> fileName;
        @Schema(title = "파일 정보")
        private List<FileInfo> fileInfo;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class FileInfo {
		@Schema(title = "파일 이름", example = "축가.mp3")
        private String name;
        @Schema(title = "다운로드 링크", type= "String", example = "/api/v1/38352658567418867")
        private String link;
    }
    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class FinalConfirm {
		@Schema(title = "업체 확인자 정보", type="String", example = "38352658567418867")
        private String org_approver;
        @Schema(title = "업체 확인", type="Boolean", example = "false")
        private boolean org_confirm;
		@Schema(title = "신랑신부 확인", type="Boolean", example = "false")
		private boolean client_confirm;
    }

    // TODO: Permission Entity
    //    @Builder.Default
    //    @JsonProperty("users")
    //    @Schema(title = "조직 소속 사용자 정보", example = "user1, user2")
    //    @ManyToMany(fetch = FetchType.LAZY)
    //    private Set<UserEntity> users = new HashSet<>();

    //////////////////////////////////////////////////////////////////////////


    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class QsheetCreateDto {
		@NotNull
        @Schema(title = "큐시트 제목", example = "최해리_230824")
        private String name;
		@NotNull
		@Schema(title = "유저 고유번호", example = "38352658567418867")
        private String userSeq;
		@Schema(title = "조직 고유번호", example = "38352658567418867")
        private String orgSeq;
		@Schema(title = "데이터 리스트")
		private List<ItemData> data;
        @Schema(title = "메모", type="String", example = "신랑 깜짝 이벤트 준비")
        private String memo;
        @NotNull
        @Schema(title = "타입", type="String", example = "template or user")
        private String type;
        @Schema(title = "업체 확인자 정보",type="String",example = "38352658567418867")
        private String org_approverSeq;
        @Schema(title = "업체 확인", type="Boolean", example = "false")
        private boolean org_confirm;
		@Schema(title = "신랑신부 확인", type="Boolean", example = "false")
		private boolean client_confirm;
        // @Schema(title = "최종 확인")
        // private FinalConfirm finalConfirm;

    }

	public void sortDataByOrderIndex() {
		if (data != null) {
			data.sort(Comparator.comparingInt(ItemData::getOrderIndex));
		}
	}
   
//    public void sortDataByOrderIndex() {
//        if (data != null) {
//            data = data.entrySet()
//                .stream()
//                .sorted(Map.Entry.comparingByValue(Comparator.comparingInt(ItemData::getOrderIndex)))
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        Map.Entry::getValue,
//                        (e1, e2) -> e1,
//                        LinkedHashMap::new
//                ));
//        }
//    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class QsheetUpdateDto {
        @NotNull
        @Schema(title = "업데이트 유저", example = "38352658567418867")
        private String updateUser;
        @Schema(title = "큐시트 제목", example = "최해리_230824")
        private String name;
		@Schema(title = "조직 고유번호", example = "38352658567418867")
        private String orgSeq;
		@Schema(title = "데이터 리스트")
		private List<ItemData> data;
        @Schema(title = "메모", type="String", example = "신랑 깜짝 이벤트 준비")
        private String memo;
        @Schema(title = "업체 확인자 정보",type="String",example = "38352658567418867")
        private String org_approverSeq;
        @Schema(title = "업체 확인", type="Boolean", example = "false")
        private boolean org_confirm;
		@Schema(title = "신랑신부 확인", type="Boolean", example = "false")
		private boolean client_confirm;
        
    }

	public void updateFromDto(QsheetUpdateDto updateDto) {
        if (updateDto.getData() != null) {
            this.data = updateDto.getData();
            this.updated_at = Instant.now();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class QsheetDownloadDto {
		@Schema(title = "사용자 리스트")
		private List<String> user;
    }

}