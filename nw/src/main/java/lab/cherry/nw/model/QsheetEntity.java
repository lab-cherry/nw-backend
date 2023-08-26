package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * ClassName : QsheetEntity
 * Type : class
 * Description : 조직과 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : QsheetRepository, QsheetServiceImpl
 * </pre>
 */
@Getter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Builder
@Table(name = "`qsheet`",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
public class QsheetEntity implements Serializable {

    @Id
    @JsonProperty("id")
    @Schema(title = "큐시트 고유번호", example = "38352658567418867") // (Long) Tsid
    private Long id;

    @Column(name = "name")
    @JsonProperty("name")
    @Schema(title = "큐시트 이름", example = "최해리_230824")
    @Size(min = 4, max = 255, message = "Minimum name length: 4 characters")
    private String name;


    @Column(name = "opening")
    @JsonProperty("opening")
    @Schema(title = "개식사", example = "")
    @Size(min = 4, max = 255, message = "Minimum biznum length: 4 characters")
    private String opening;

   
    @Column(name = "light")
    @JsonProperty("light")
    @Schema(title = "화촉점화", example = "")
    @Size(min = 4, max = 255, message = "Minimum contact length: 4 characters")
    private String light;

    @Column(name = "groom_entrance")
    @JsonProperty("groom_entrance")
    @Schema(title = "신랑입장", example = "")
    private String groom_entrance;

    @Column(name = "bride_entrance")
    @JsonProperty("bride_entrance")
    @Schema(title = "신부입장", example = "")
    private String bride_entrance;
    

    @Column(name = "bow")
    @JsonProperty("bow")
    @Schema(title = "맞절", example = "")
    private String bow;
    
    @Column(name = "vow")
    @JsonProperty("vow")
    @Schema(title = "혼인서약", example = "true")
    private String vow;
    

    @Column(name = "declaration")
    @JsonProperty("declaration")
    @Schema(title = "성혼선언", example = "true")
    private String declaration;
    

    @Column(name = "song")
    @JsonProperty("song")
    @Schema(title = "축가", example = "true")
    private String song;
    
    @Column(name = "greeting")
    @JsonProperty("greeting")
    @Schema(title = "인사", example = "true")
    private String greeting;

    @Column(name = "parade")
    @JsonProperty("parade")
    @Schema(title = "행진", example = "true")
    private String parade;
    

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
}