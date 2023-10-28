package lab.cherry.nw.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * ClassName : UserEntity
 * Type : class
 * Description : User와 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : UserRepository, UserServiceImpl
 * </pre>
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "email_auth")
public class EmailAuthEntity implements Serializable {

    @Id
    private String id;
    @DBRef
    private UserEntity user;
    @Email
    @Size(min = 3, max = 40)
    private String email;
    private String token;
    private LocalDateTime expired;
    
    public void updateExpireDate() {
        this.expired = LocalDateTime.now().plusMinutes(5L);
    }
    
    public void updateToken() {
        this.token = new ObjectId().toString();
    }
    
    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class EmailAuthRequestDto {

        @NotBlank
        @Email
        @Schema(title = "사용자 이메일", example = "admin@nw.com")
        @Size(min = 3, max = 40)
        private String email;

        @NotBlank
        @Schema(title = "인증 토큰", example = "653b34d4ec37354cf3d0ea78")
        private String token;

    }
}
