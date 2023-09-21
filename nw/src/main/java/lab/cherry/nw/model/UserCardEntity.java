package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 * ClassName : UserCardEntity
 * Type : class
 * Description : User와 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : UserRepository, UserServiceImpl
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "usercards")
@JsonPropertyOrder({ "id", "userName", "userEmail", "groom","userinfo", "bride", "note", "resDate", "status","weddingDate", "created_at","update_at" })
public class UserCardEntity implements Serializable {

    @Id
    @JsonProperty("userCardSeq")
    @Schema(title = "고객카드 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private String id;

    @DBRef
    @JsonProperty("userinfo")
    @Schema(title = "사용자 정보", example = "")
    private UserEntity userinfo;

    @JsonProperty("groom")
    @Schema(title = "신랑측 정보", example = "")
    private Map groom;

    @JsonProperty("bride")
    @Schema(title = "신부측 정보", example = "")
    private Map bride;

    @JsonProperty("note")
    @Schema(title = "비고", example = "가을")
    private String note;

    @JsonProperty("resDate")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "예약 날짜", example = "2023-07-04 12:00:00")
    private String resDate;

    @JsonProperty("weddingDate")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "예식 날짜", example = "2023-07-04 12:00:00")
    private String weddingDate;

    @JsonProperty("status")
    @Schema(title = "진행 상태", example = "진행 중")
    private String status;

    @JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "고객카드 생성 시간", example = "2023-07-04 12:00:00")
    private Instant created_at;

    @JsonProperty("update_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "고객카드 수정 시간", example = "2023-07-04 12:00:00")
    private Instant update_at;




//////////////////////////////////////////////////////////////////////////

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class CreateDto {


        private String userinfo;

        @JsonProperty("groom")
        @Schema(title = "신랑측 정보", example = "")
        private Map groom;

        @JsonProperty("bride")
        @Schema(title = "신부측 정보", example = "")
        private Map bride;

        @JsonProperty("note")
        @Schema(title = "비고", example = "가을")
        private String note;

        @JsonProperty("resDate")
        @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
        @Schema(title = "예약 날짜", example = "2023-07-04 12:00:00")
        private String resDate;

        @JsonProperty("weddingDate")
        @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
        @Schema(title = "예식 날짜", example = "2023-07-04 12:00:00")
        private String weddingDate;

        @JsonProperty("status")
        @Schema(title = "진행 상태", example = "진행 중")
        private String status;


        @JsonProperty("created_at")
        @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
        @Schema(title = "고객카드 생성 시간", example = "2023-07-04 12:00:00")
        private Instant created_at;


    }


    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class UpdateDto {

        private String userinfo;

        @JsonProperty("groom")
        @Schema(title = "신랑측 정보", example = "")
        private Map groom;

        @JsonProperty("bride")
        @Schema(title = "신부측 정보", example = "")
        private Map bride;

        @JsonProperty("note")
        @Schema(title = "비고", example = "가을")
        private String note;

        @JsonProperty("resDate")
        @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
        @Schema(title = "예약 날짜", example = "2023-07-04 12:00:00")
        private String resDate;

        @JsonProperty("weddingDate")
        @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
        @Schema(title = "예식 날짜", example = "2023-07-04 12:00:00")
        private String weddingDate;

        @JsonProperty("status")
        @Schema(title = "진행 상태", example = "진행 중")
        private String status;

        @JsonProperty("update_at")
        @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
        @Schema(title = "고객카드 수정 시간", example = "2023-07-04 12:00:00")
        private Instant update_at;

    }
}
