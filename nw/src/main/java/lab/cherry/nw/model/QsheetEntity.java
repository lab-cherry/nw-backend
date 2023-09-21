package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.minio.messages.Item;
import io.swagger.v3.oas.annotations.media.Schema;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
    @Schema(title = "큐시트 고유번호", example = "38352658567418867") // (Long) Tsid
    private String id;

    @DBRef
    @JsonProperty("userSeq")
    @Schema(title = "유저 고유번호", example = "38352658567418867") // (Long) Tsid
    private UserEntity userid;

    @DBRef
    @JsonProperty("orgSeq")
    @Schema(title = "조직 정보", example = "38352658567418867") // (Long) Tsid
    private OrgEntity orgid;


    @JsonProperty("name")
    @Schema(title = "큐시트 이름", example = "최해리_230824")
    @Size(min = 4, max = 255, message = "Minimum name length: 4 characters")
    private String name;

    @JsonProperty("data")
	@Schema(title = "큐시트 내용", example = "") // (Long) Tsid
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
        private int orderIndex;
		private String process;
        private String content;
        private String actor;
        private String note;
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
    public static class CreateDto {

        @Schema(title = "큐시트 이름", example = "최해리_230824")
        @Size(min = 4, max = 20)
        private String name;
        private String userSeq;
        private String orgSeq;
		private List<ItemData> data;
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
		private List<ItemData> data;
    }

     public void updateFromDto(UpdateDto updateDto) {
        if (updateDto.getData() != null) {
            this.data = updateDto.getData();
            this.updated_at = Instant.now();
        }
    }


}
