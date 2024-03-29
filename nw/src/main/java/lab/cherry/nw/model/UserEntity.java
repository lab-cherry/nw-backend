package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;
import java.time.Instant;

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
@Document(collection = "users")
@JsonPropertyOrder({ "id", "userId", "userName", "userEmail", "userRole", "userType", "userEnabled", "emailVerified", "userRole", "userOrgs", "created_at" })
public class UserEntity implements Serializable {

    @Id
    @JsonProperty("userSeq")
    @Schema(title = "사용자 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private String id;

    @NotNull
    @JsonProperty("userId")
    @Schema(title = "사용자 아이디", example = "admin")
    @Size(min = 4, max = 10, message = "Minimum userId length: 4 characters")
    private String userid;

    @NotNull
    @JsonProperty("userName")
    @Schema(title = "사용자 이름", example = "홍길동")
    @Size(min = 2, max = 10, message = "Minimum username length: 4 characters")
    private String username;

    @NotNull
    @JsonProperty("userEmail")
    @Schema(title = "사용자 이메일", example = "test@test.com")
    @Email(message = "Email Should Be Valid")
    private String email;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(title = "사용자 비밀번호", example = "Pa@sW0rd")
    @Size(min = 3, message = "Minimum password length: 8 characters")
    private String password;

	@JsonProperty("userPhoto")
	@Schema(title = "사용자 사진")
	private Object photo;

    @JsonProperty("userEnabled")
    @Schema(title = "사용자 활성화 여부", example = "true")
    private Boolean enabled;

    @JsonProperty("emailVerified")
    @Schema(title = "이메일 인증 여부", example = "true")
    private Boolean isEmailVerified;

    @JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "사용자 생성 시간", example = "2023-07-04 12:00:00")
    private Instant created_at;

    @DBRef
    @JsonProperty("userRole")
    @Schema(title = "사용자 권한 정보", example = "ROLE_USER")
    private RoleEntity role;

    @DBRef
    @JsonProperty("userOrg")
    @Schema(title = "Org 정보", example = "더모멘트")
//    private Set<OrgEntity> orgs = new HashSet<>();
	private OrgEntity org;
    
    public void emailVerifiedSuccess() {
        this.isEmailVerified = true;
    }
    
    public void resetPassword(String password) {
        this.password = password;
    }
    
    public void changeOrg(OrgEntity org) {
        this.org = org;
    }
    
    public void changeRole(RoleEntity role) {
        this.role = role;
    }
    
    
    public void editImage(Object image) {
        this.photo = image;
    }

//////////////////////////////////////////////////////////////////////////

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class UserRegisterDto {

        @NotBlank
        @Schema(title = "사용자 아이디", example = "admin")
        @Size(min = 4, max = 10)
        private String userId;

        @NotBlank
        @Schema(title = "사용자 이름", example = "홍길동")
        @Size(min = 2, max = 10, message = "Minimum username length: 2 characters")
        private String userName;

        @NotBlank
        @Email
        @Schema(title = "사용자 이메일", example = "admin@nw.com")
        @Size(min = 3, max = 40)
        private String userEmail;

        @Schema(title = "타입", example = "user | org")
        private String userType;

        @NotBlank
        @Schema(title = "사용자 비밀번호", example = "Pa@sW0rd")
        @Size(min = 3, message = "Minimum password length: 3 characters")
        private String userPassword;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class UserLoginDto {

        @NotBlank
        @Schema(title = "사용자 아이디", example = "admin")
        @Size(min = 4, max = 10, message = "Minimum admin length: 4 characters")
        private String userId;

        @NotBlank
        @Schema(title = "사용자 비밀번호", example = "Pa@sW0rd")
        @Size(min = 3, message = "Minimum password length: 8 characters")
        private String userPassword;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class UserUpdateDto {

        @Schema(title = "사용자 이름", example = "관리자")
        @Size(min = 2, max = 10)
        private String userName;

        @Schema(title = "사용자 이메일", example = "admin@innogrid.com")
        @Email
        @Size(min = 3, max = 40)
        private String userEmail;

        @Schema(title = "사용자 비밀번호", example = "Pa@sW0rd")
        @Size(min = 3, max = 40)
        private String userPassword;

		@Schema(title = "사용자 조직", example = "")
        @Size(min = 1, max = 40)
        private String orgId;

		@Schema(title = "사용자 권한", example = "")
        @Size(min = 1, max = 40)
        private String roleId;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class UserForgotPassword {

        @Schema(title = "사용자 아이디", example = "admin")
        @Size(min = 4, max = 10)
        private String userId;

        @Schema(title = "사용자 이메일", example = "admin@innogrid.com")
        @Email
        @Size(min = 3, max = 40)
        private String userEmail;

    }
}
