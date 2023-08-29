package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.UUID;

/**
 * <pre>
 * ClassName : RoleEntity
 * Type : class
 * Description : Role과 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : RoleRepository
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "roles")
public class RoleEntity implements Serializable {

    @Id
    @JsonProperty("roleSeq")
    @Schema(title = "사용자 고유번호", example = "dad6d905-2414-4d4e-ab01-bcbccdb6f677")
    private String id;

    @NotNull
    @JsonProperty("roleName")
    @Schema(title = "권한 이름", example = "ROLE_USER")
    private String name;

    //////////////////////////////////////////////////////////////////////////

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class CreateDto {

        @Schema(title = "권한 이름", example = "ADMIN")
        @Size(min = 4, max = 20, message = "Minimum name length: 4 characters")
        private String name;

    }

}
