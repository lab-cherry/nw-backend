package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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

/**
 * <pre>
 * ClassName : OrgEntity
 * Type : class
 * Description : 조직과 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : OrgRepository, OrgServiceImpl
 * </pre>
 */
@Getter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Builder
@Table(name = "`organizations`",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
})
@JsonPropertyOrder({ "id", "orgName", "orgBiznum", "orgContact", "orgEnabled", "created_at" })
public class OrgEntity implements Serializable {

    @Id
    @JsonProperty("id")
    @Schema(title = "조직 조직 고유번호", example = "38352658567418867") // (Long) Tsid
    private Long id;

    @NotNull
    @Column(name = "name")
    @JsonProperty("orgName")
    @Schema(title = "조직 이름", example = "더모멘트")
    @Size(min = 4, max = 255, message = "Minimum name length: 4 characters")
    private String name;

    @NotNull
    @Column(name = "biznum")
    @JsonProperty("orgBiznum")
    @Schema(title = "조직 사업자번호", example = "123-45-67890")
    @Size(min = 4, max = 255, message = "Minimum biznum length: 4 characters")
    private String biznum;

    @NotNull
    @Column(name = "contact")
    @JsonProperty("orgContact")
    @Schema(title = "조직 연락처", example = "02-0000-0000")
    @Size(min = 4, max = 255, message = "Minimum contact length: 4 characters")
    private String contact;

    @Column(name = "enabled")
    @JsonProperty("orgEnabled")
    @Schema(title = "조직 활성화 여부", example = "true")
    private boolean enabled;

    @JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "조직 생성 시간", example = "2023-07-04 12:00:00")
    @CreationTimestamp
    private Timestamp created_at;        

    // TODO: Permission Entity
//    @Builder.Default
//    @JsonProperty("users")
//    @Schema(title = "조직 소속 사용자 정보", example = "user1, user2")
//    @ManyToMany(fetch = FetchType.LAZY)
//    private Set<UserEntity> users = new HashSet<>();

    //////////////////////////////////////////////////////////////////////////

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class CreateDto {

        @NotBlank
        @Schema(title = "조직 이름", example = "더모멘트")
        @Size(min = 4, max = 20, message = "Minimum name length: 4 characters")
        private String name;

        @NotBlank
        @Schema(title = "조직 사업자번호", example = "123-45-67890")
        @Size(min = 4, max = 40, message = "Minimum biznum length: 4 characters")
        private String biznum;

        @NotBlank
        @Schema(title = "조직 연락처", example = "02-0000-0000")
        @Size(min = 4, max = 40, message = "Minimum contact length: 4 characters")
        private String contact;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class UpdateDto {

        @NotBlank
        @Schema(title = "조직 이름", example = "더모멘트")
        @Size(min = 4, max = 20, message = "Minimum name length: 4 characters")
        private String name;

        @NotBlank
        @Schema(title = "조직 사업자번호", example = "123-45-67890")
        @Size(min = 4, max = 40, message = "Minimum biznum length: 4 characters")
        private String biznum;

        @NotBlank
        @Schema(title = "조직 연락처", example = "02-0000-0000")
        @Size(min = 4, max = 40, message = "Minimum contact length: 4 characters")
        private String contact;

    }
}