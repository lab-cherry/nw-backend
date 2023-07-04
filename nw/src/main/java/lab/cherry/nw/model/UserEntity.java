package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

/**
 * <pre>
 * ClassName : UserEntity
 * Type : class
 * Descrption : User와 관련된 Entity를 구성하고 있는 클래스입니다.
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

    @Schema(title = "사용자 Id", example = "1")
    @JsonProperty("userId")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Schema(title = "사용자 이름", example = "홍길동")
    @JsonProperty("userName")
    @Column(name = "username", unique = true, nullable = false)
    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    private String username;

    @Schema(title = "사용자 이메일", example = "test@test.com")
    @JsonProperty("userEmail")
    @Email(message = "Email Should Be Valid")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Schema(title = "사용자 비밀번호", example = "Pa@sW0rd")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    @Size(min = 3, message = "Minimum password length: 8 characters")
    private String password;

    @Schema(title = "사용자 활성화 여부", example = "true")
    @JsonProperty("userEnabled")
    @Column(name = "enabled")
    private boolean enabled;

    @Schema(title = "사용자 생성 시간", example = "2023-07-04T12:00:00.000+00:00")
    @CreationTimestamp
    private Timestamp created_at;

    @Schema(title = "사용자 권한 정보", example = "['ROLE_USER']")
//    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JsonProperty("userRoles")
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
}
