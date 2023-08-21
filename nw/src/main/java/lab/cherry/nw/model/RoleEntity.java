package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * ClassName : RoleEntity
 * Type : class
 * Description : Role과 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : RoleRepository
 * </pre>
 */
@Getter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Builder
@Table(name = "`roles`",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
public class RoleEntity implements Serializable {

    private final String excludeThisField = "users";

    @Id
    @JsonProperty("roleId")
    @Schema(title = "권한 Id", example = "1")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotNull
    @Column(name = "name")
    @JsonProperty("roleName")
    @Schema(title = "권한 이름", example = "ROLE_USER")
    private String name;

    @Builder.Default
    @JsonIgnore
    @Schema(title = "사용자 정보")
    @ManyToMany(mappedBy = "roles", fetch=FetchType.EAGER)
    private Set<UserEntity> users = new HashSet<>();
}
