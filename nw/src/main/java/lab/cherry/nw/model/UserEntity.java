package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserEntity implements Serializable {

    @Id
    @JsonProperty("userId")
    @Schema(title = "사용자 고유번호", example = "38352658567418867")
    private Long id;

    @NotNull
    @Column(name = "username")
    @JsonProperty("userName")
    @Schema(title = "사용자 이름", example = "홍길동")
    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
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

    @Schema(title = "사용자 생성 시간", example = "2023-07-04T12:00:00.000+00:00")
    @CreationTimestamp
    private Timestamp created_at;

    @Builder.Default
    @JsonProperty("userRoles")
    @Schema(title = "사용자 권한 정보", example = "['ROLE_USER']")
//    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id",referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",referencedColumnName = "id"
            )
    )
    private Set<RoleEntity> roles  = new HashSet<>();

//////////////////////////////////////////////////////////////////////////

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class RegisterDto {

        @NotBlank
        @Schema(title = "사용자 이름", example = "홍길동")
        @Size(min = 3, max = 20)
        private String username;

        @NotBlank
        @Email
        @Schema(title = "사용자 이메일", example = "test@test.com")
        @Size(min = 3, max = 40)
        private String email;

        @NotBlank
        @Schema(title = "사용자 비밀번호", example = "Pa@sW0rd")
        @Size(min = 3, max = 40)
        private String password;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class LoginDto {

        @NotBlank
        @Schema(title = "사용자 이름", example = "홍길동")
        @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
        private String username;

        @NotBlank
        @Schema(title = "사용자 비밀번호", example = "Pa@sW0rd")
        @Size(min = 3, message = "Minimum password length: 8 characters")
        private String password;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class UpdateDto {

        @NotBlank
        @Schema(title = "사용자 이름", example = "홍길동")
        @Size(min = 3, max = 20)
        private String username;

        @NotBlank
        @Schema(title = "사용자 이메일", example = "test@test.com")
        @Email
        @Size(min = 3, max = 40)
        private String email;

        @NotBlank
        @Schema(title = "사용자 비밀번호", example = "Pa@sW0rd")
        @Size(min = 3, max = 40)
        private String password;

        @Schema(title = "사용자 권한 정보", example = "['ROLE_USER']")
        private String[] roles;
    }
}
