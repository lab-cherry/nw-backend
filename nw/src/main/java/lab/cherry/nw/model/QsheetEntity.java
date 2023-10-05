package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;


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
@JsonPropertyOrder({ "id", "name", "opening"})
public class QsheetEntity implements Serializable {

    @Id
    @JsonProperty("qsheetSeq")
    @Schema(title = "큐시트 고유번호",type="String", example = "38352658567418867") // (Long) Tsid
    private String id;

    @DBRef
    @JsonProperty("userSeq")
    @Schema(title = "유저 고유번호", type="String",example = "38352658567418867") // (Long) Tsid
    private UserEntity userid;

    @DBRef
    @JsonProperty("orgSeq")
    @Schema(title = "조직 정보", type="String",example = "38352658567418867") // (Long) Tsid
    private OrgEntity orgid;


    @JsonProperty("name")
    @Schema(title = "큐시트 이름", type="String",example = "최해리_230824")
    @Size(min = 4, max = 255, message = "Minimum name length: 4 characters")
    private String name;

    @JsonProperty("data")
	@Schema(title = "큐시트 내용",type="List", example = "[{'orderIndex':1, }]")

    private List<ItemData> data;

    @JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "큐시트 생성 시간", example = "2023-07-04 12:00:00")
    private Instant created_at;

    @JsonProperty("updated_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "큐시트 업데이트 시간", example = "2023-07-04 12:00:00")
    private Instant updated_at;


    @Getter
    @Builder
    public static class ItemData {
		@Schema(title = "큐시트 순서", example = "1")
        private int orderIndex;
		@Schema(title = "큐시트 순서명", example = "축가")
		private String process;
		@Schema(title = "큐시트 내용", example = "니가사는그집-박진영")
        private String content;
		@Schema(title = "큐시트 행위자", example = "신부")
        private String actor;
		@Schema(title = "메모", example = "신부가 노래를 못함")
        private String note;
		@Schema(title = "파일위치", example = "./")
        private String filePath;
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
    public static class CreateQsheetDto {

        @Schema(title = "큐시트 데이터", example = "최해리_230824")
        @Size(min = 4, max = 20)
		@NotNull
        private String name;
		@Schema(title = "유저 고유번호", example = "38352658567418867")
        private String userSeq;
		@Schema(title = "조직 고유번호", example = "38352658567418867")
        private String orgSeq;
//		@Schema(title = "데이터", implementation=List<ItemData>.class)
		@Schema(title = "데이터 리스트")
		//example="[{\"orderIndex1\": \"1\", \"process\": \"축가\", \"content\": \"니가사는그집-박진영\", \"actor\": \"신부\", \"note\": \"신부가 노래를 잘함\", \"filePathr\": \"./\"}]
		private List<ItemData> data;
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
    public static class UpdateDto {
		@Schema(title = "데이터 리스트")
		private List<ItemData> data;
    }

     public void updateFromDto(UpdateDto updateDto) {
        if (updateDto.getData() != null) {
            this.data = updateDto.getData();
            this.updated_at = Instant.now();
        }
    }


}
