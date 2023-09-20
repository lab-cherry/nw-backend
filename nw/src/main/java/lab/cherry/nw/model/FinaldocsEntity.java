package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.time.Instant;
import java.util.Map;

/**
 * <pre>
 * ClassName : UserEntity
 * Type : class
 * Description : User와 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : UserRepository, UserServiceImpl
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "finaldocs")
@JsonPropertyOrder({ "id", "finaldocsName","finaltemplid","userid", "orgid","content","updated_at","created_at" })
public class FinaldocsEntity implements Serializable {

    @Id
    @JsonProperty("finaldocsSeq")
    @Schema(title = "최종확인서 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private String id;

    @JsonProperty("finaltemplid")
    @Schema(title = "최종확인서 템플릿 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private FinalTemplEntity finaltemplid;

    @DBRef
    @JsonProperty("userid")
    @Schema(title = "사용자 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private UserEntity userid;

    @DBRef
    @JsonProperty("orgid")
    @Schema(title = "조직 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private OrgEntity orgid;

    @JsonProperty("finaldocsName")
    @Schema(title = "최종확인서 이름", example = "문서1")
    @Size(min = 4, max = 255, message = "Minimum name length: 4 characters")
    private String name;

    @JsonProperty("content")
    @Schema(title = "최종확인서 내용", example = "")
    private Map content;
//    private Map<String, Object> groom;

    @JsonProperty("updated_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "최종확인서 수정 시간", example = "2023-07-04 12:00:00")
    private Instant updated_at;

    @JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "최종확인서 생성 시간", example = "2023-07-04 12:00:00")
    private Instant created_at;


//////////////////////////////////////////////////////////////////////////

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class CreateDto {
        @Schema(title = "최종확인서 이름", example = "더모멘트")
        @Size(min = 4, max = 20, message = "Minimum name length: 4 characters")
        private String name;

        private String userid;

        private String orgid;

        private  String finaltemplid;

        private Map content;

    }


    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class UpdateDto {

        private String id;

        private String finaltemplid;

        private String userid;

        private String orgid;

        private Map content;

        private Instant updated_at;

    }



}
