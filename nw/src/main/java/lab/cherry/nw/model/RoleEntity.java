package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Id
    @Column(name = "id")
    @JsonProperty("roleId")
    @Schema(title = "사용자 고유번호", example = "38352658567418867") // (Long) Tsid
    private Long id;

    @NotNull
    @Column(name = "name")
    @JsonProperty("roleName")
    @Schema(title = "권한 이름", example = "ROLE_USER")
    private String name;

    //////////////////////////////////////////////////////////////////////////

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class CreateDto {

        @NotBlank
        @Schema(title = "권한 이름", example = "ADMIN")
        @Size(min = 4, max = 20, message = "Minimum name length: 4 characters")
        private String name;

    }

}
