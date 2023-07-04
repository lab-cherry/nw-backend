package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * ClassName : RoleEntity
 * Type : class
 * Descrption : Role과 관련된 Entity를 구성하고 있는 클래스입니다.
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

    @Schema(title = "권한 Id", example = "1")
    @JsonProperty("roleId")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Schema(title = "권한 이름", example = "ROLE_USER")
    @JsonProperty("roleName")
    @Column(name = "name", nullable = false)
    private String name;

    @Schema(title = "사용자 정보")
    @JsonIgnore
    @ManyToMany(mappedBy = "roles", fetch=FetchType.EAGER)
    private Set<UserEntity> users = new HashSet<>();
}
