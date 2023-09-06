package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
import java.util.Comparator;
import java.util.LinkedHashMap;
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
    @JsonProperty("user")
    @Schema(title = "유저 고유번호", example = "38352658567418867") // (Long) Tsid
    private UserEntity userid;

    @DBRef
    @JsonProperty("org")
    @Schema(title = "조직 고유번호", example = "38352658567418867") // (Long) Tsid
    private OrgEntity orgid;

    @JsonProperty("name")
    @Schema(title = "큐시트 이름", example = "최해리_230824")
    @Size(min = 4, max = 255, message = "Minimum name length: 4 characters")
    private String name;

    @JsonProperty("data")
    private Map<String, ItemData> data;

    @Getter
    @Builder
    public static class ItemData {
        private int orderIndex;
        private String content;
    }
    //
    //    @JsonProperty("light")
    //    @Schema(title = "화촉점화", example = "")
    //    @Size(min = 4, max = 255, message = "Minimum contact length: 4 characters")
    //    private String light;
    //
    //    @JsonProperty("groom_entrance")
    //    @Schema(title = "신랑입장", example = "")
    //    private String groom_entrance;
    //
    //    @JsonProperty("bride_entrance")
    //    @Schema(title = "신부입장", example = "")
    //    private String bride_entrance;
    //
    //
    //    @JsonProperty("bow")
    //    @Schema(title = "맞절", example = "")
    //    private String bow;
    //
    //    @JsonProperty("vow")
    //    @Schema(title = "혼인서약", example = "")
    //    private String vow;
    //
    //
    //    @JsonProperty("declaration")
    //    @Schema(title = "성혼선언", example = "")
    //    private String declaration;
    //
    //
    //    @JsonProperty("song")
    //    @Schema(title = "축가", example = "")
    //    private String song;
    //
    //
    //    @JsonProperty("greeting")
    //    @Schema(title = "인사", example = "")
    //    private String greeting;
    //
    //
    //    @JsonProperty("parade")
    //    @Schema(title = "행진", example = "")
    //    private String parade;


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
        private Map<String, ItemData> data;
    }

    public void sortDataByOrderIndex() {
        if (data != null) {
            data = data.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparingInt(ItemData::getOrderIndex)))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        }
    }


        //
    //        @Schema(title = "화촉점화", example = "")
    //        @Size(min = 4, max = 40, message = "Minimum contact length: 4 characters")
    //        private String light;
    //
    //        @Schema(title = "신랑 입장", example = "")
    //        @Size(min = 4, max = 40, message = "Minimum contact length: 4 characters")
    //        private String groom_entrance;
    //
    //        @Schema(title = "신부입장", example = "")
    //        @Size(min = 4, max = 40, message = "Minimum contact length: 4 characters")
    //        private String bride_entrance;
    //
    //        @Schema(title = "맞절", example = "")
    //        @Size(min = 4, max = 40, message = "Minimum contact length: 4 characters")
    //        private String bow;
    //
    //        @Schema(title = "혼인서약", example = "")
    //        @Size(min = 4, max = 40, message = "Minimum contact length: 4 characters")
    //        private String vow;
    //
    //        @Schema(title = "성혼선언", example = "")
    //        @Size(min = 4, max = 40, message = "Minimum contact length: 4 characters")
    //        private String declaration;
    //
    //        @Schema(title = "축가", example = "")
    //        @Size(min = 4, max = 40, message = "Minimum contact length: 4 characters")
    //        private String song;
    //
    //        @Schema(title = "인사", example = "")
    //        @Size(min = 4, max = 40, message = "Minimum contact length: 4 characters")
    //        private String greeting;
    //
    //        @Schema(title = "행진", example = "")
    //        @Size(min = 4, max = 40, message = "Minimum contact length: 4 characters")
    //        private String parade;

}
