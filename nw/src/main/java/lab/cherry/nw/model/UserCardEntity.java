package lab.cherry.nw.model;

import java.io.Serializable;
import java.time.Instant;
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

/**
 * <pre>
 * ClassName : UserCardEntity
 * Type : class
 * Description : UserCard와 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : UserCardRepository, UserCardServiceImpl
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "usercards")
@JsonPropertyOrder({ "id", "userName", "userEmail", "weddinghall", "groom","userinfo", "bride", "note", "resDate", "status","weddingDate", "created_at","update_at" })
public class UserCardEntity implements Serializable {

    @Id
    @JsonProperty("userCardSeq")
    @Schema(title = "고객카드 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private String id;

    @DBRef
    @JsonProperty("userinfo")
    @Schema(title = "사용자 정보", example = "[]")
    private UserEntity userinfo;

    @DBRef
    @JsonProperty("weddinghall")
    @Schema(title = "예식장 정보", example = "[]")
    private WeddinghallEntity weddinghall;

    @JsonProperty("groom")
    @Schema(title = "신랑측 정보", example = "[]")
    private UserFamily groom;

    @JsonProperty("bride")
    @Schema(title = "신부측 정보", example = "[]")
    private UserFamily bride;

    @JsonProperty("note")
	@Size(max = 500, message = "Maximum contact length: 500 characters")
    @Schema(title = "비고", example = "가을")
    private String note;

    @JsonProperty("resDate")
    @JsonFormat(locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "예약 날짜", example = "2023-08-29T05:11:38.002Z")
    private String resDate;

    @JsonProperty("weddingDate")
    @JsonFormat(locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "예식 날짜", example = "2023-08-29T05:11:38.002Z")
    private String weddingDate;

    @JsonProperty("status")
	@Size(min = 2, max = 20, message = "Minimum contact length: 2 characters")
    @Schema(title = "진행 상태", example = "진행중")
    private String status;

    @JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "고객카드 생성 시간", example = "2023-08-29T05:11:38.002Z")
    private Instant created_at;

    @JsonProperty("update_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "고객카드 수정 시간", example = "2023-08-29T05:11:38.002Z")
    private Instant update_at;


    @Getter
    @NoArgsConstructor @AllArgsConstructor
    public static class UserFamily {

        @NotBlank
        @Schema(title = "신랑/신부 이름", example = "신짱구")
        private String name;

        @Schema(title = "신랑/신부 이메일", example = "jjanggu@nw.com")
        private String email;

        @Schema(title = "신랑/신부 연락처", example = "010-1234-5678")
        private String contact;

        @Schema(title = "신랑/신부 아버지 성함", example = "신영만")
        private String father;

        @Schema(title = "신랑/신부 어머니 성함", example = "봉미선")
        private String mother;
    }
//////////////////////////////////////////////////////////////////////////

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class UserCardCreateDto {

        @NotBlank
		@Schema(title = "사용자 고유번호", example = "64f82e492948d933edfaa9c0")
        private String userSeq;

        @NotBlank
        @Schema(title = "예식장 이름", example = "더모멘트")
        private String weddinghallName;

        @Schema(title = "신랑측 정보")
        private UserFamily groom;

        @Schema(title = "신부측 정보")
        private UserFamily bride;

        @NotBlank
		@Size(max = 500, message = "Maximum contact length: 500 characters")
        @Schema(title = "비고", example = "가을")
        private String note;

        @NotBlank
        private String resDate;

        @NotBlank
        @Schema(title = "예식 날짜", example = "2023-08-29T05:11:38.002Z")
        private String weddingDate;

        @NotBlank
		@Size(min = 2, max = 20, message = "Minimum contact length: 2 characters")
        @Schema(title = "진행 상태", example = "진행중")
        private String status;

    }


    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class UserCardUpdateDto {

        @NotBlank
		@Schema(title = "사용자 고유번호", example = "64f82e492948d933edfaa9c0")
        private String userSeq;

        @Schema(title = "예식장 이름", example = "더모멘트")
        private String weddinghallName;

        @Schema(title = "신랑측 정보")
        private UserFamily groom;

        @Schema(title = "신부측 정보")
        private UserFamily bride;

		@Size(max = 500, message = "Maximum contact length: 500 characters")
        @Schema(title = "비고", example = "가을")
        private String note;

        @Schema(title = "예약 날짜", example = "2023-08-29T05:11:38.002Z")
        private String resDate;

        @Schema(title = "예식 날짜", example = "2023-08-29T05:11:38.002Z")
        private String weddingDate;

		@Size(min = 2, max = 20, message = "Minimum contact length: 2 characters")
		@Schema(title = "진행 상태", example = "진행중")
        private String status;

    }
}
