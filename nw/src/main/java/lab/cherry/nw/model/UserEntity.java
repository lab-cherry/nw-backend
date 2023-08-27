package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * ClassName : UserEntity
 * Type : class
 * Description : User와 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : UserRepository, UserServiceImpl
 * </pre>
 */
@Getter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Builder
@Table(name = "`users`",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@JsonPropertyOrder({ "id", "userId", "userName", "userEmail", "userRole", "userEnabled", "userRole", "userOrgs", "created_at" })
public class UserEntity implements Serializable {

    @Id
    @JsonProperty("userSeq")
    @Schema(title = "사용자 고유번호", example = "38352658567418867") // (Long) Tsid
    private Long id;

    @NotNull
    @Column(name = "userId")
    @JsonProperty("userId")
    @Schema(title = "사용자 아이디", example = "admin")
    @Size(min = 4, max = 10, message = "Minimum userId length: 4 characters")
    private String userid;

    @NotNull
    @Column(name = "username")
    @JsonProperty("userName")
    @Schema(title = "사용자 이름", example = "홍길동")
    @Size(min = 2, max = 10, message = "Minimum username length: 4 characters")
    private String username;

    @NotNull
    @Column(name = "email")
    @JsonProperty("userEmail")
    @Schema(title = "사용자 이메일", example = "test@test.com")
    @Email(message = "Email Should Be Valid")
    private String email;

    @NotNull
    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(title = "사용자 비밀번호", example = "Pa@sW0rd")
    @Size(min = 3, message = "Minimum password length: 8 characters")
    private String password;

    @Column(name = "enabled")
    @JsonProperty("userEnabled")
    @Schema(title = "사용자 활성화 여부", example = "true")
    private boolean enabled;

    @JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "사용자 생성 시간", example = "2023-07-04 12:00:00")
    @CreationTimestamp
    private Timestamp created_at;

    @JsonProperty("userRole")
    @Schema(title = "사용자 권한 정보", example = "ROLE_USER")
    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(
                    name = "user_id",referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",referencedColumnName = "id"
            )
    )
    private RoleEntity role;

    @Builder.Default
    @JsonProperty("userOrgs")
    @Schema(title = "Org 정보", example = "더모멘트")
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_orgs",
            joinColumns = @JoinColumn(
                    name = "user_id",referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "org_id",referencedColumnName = "id"
            )
    )
    private Set<OrgEntity> orgs = new HashSet<>();

//////////////////////////////////////////////////////////////////////////

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class RegisterDto {

        @NotBlank
        @Schema(title = "사용자 아이디", example = "admin")
        @Size(min = 4, max = 10)
        private String userid;

        @NotBlank
        @Schema(title = "사용자 이름", example = "홍길동")
        @Size(min = 2, max = 10, message = "Minimum username length: 4 characters")
        private String username;

        @NotBlank
        @Email
        @Schema(title = "사용자 이메일", example = "admin@innogrid.com")
        @Size(min = 3, max = 40)
        private String email;

        @NotBlank
        @Schema(title = "사용자 비밀번호", example = "Pa@sW0rd")
        @Size(min = 3, message = "Minimum password length: 8 characters")
        private String password;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class LoginDto {

        @NotBlank
        @Schema(title = "사용자 아이디", example = "admin")
        @Size(min = 4, max = 10, message = "Minimum admin length: 4 characters")
        private String userid;

        @NotBlank
        @Schema(title = "사용자 비밀번호", example = "Pa@sW0rd")
        @Size(min = 3, message = "Minimum password length: 8 characters")
        private String password;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class UpdateDto {

        @Schema(title = "사용자 이름", example = "관리자")
        @Size(min = 2, max = 10)
        private String username;

        @Schema(title = "사용자 이메일", example = "admin@innogrid.com")
        @Email
        @Size(min = 3, max = 40)
        private String email;

        @Schema(title = "사용자 비밀번호", example = "Pa@sW0rd")
        @Size(min = 3, max = 40)
        private String password;

    }
}
