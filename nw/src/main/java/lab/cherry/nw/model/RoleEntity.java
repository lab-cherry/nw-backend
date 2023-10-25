package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.time.Instant;

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
    @Schema(title = "사용자 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private String id;

    @NotNull
    @JsonProperty("roleName")
    @Schema(title = "권한 이름", example = "ROLE_USER")
    private String name;

    @JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "권한 생성 시간", example = "2023-07-04 12:00:00")
    private Instant created_at;

    //////////////////////////////////////////////////////////////////////////

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class RoleCreateDto {

        @Schema(title = "권한 이름", example = "ADMIN")
        @Size(min = 4, max = 20, message = "Minimum name length: 4 characters")
        private String name;

    }

}
