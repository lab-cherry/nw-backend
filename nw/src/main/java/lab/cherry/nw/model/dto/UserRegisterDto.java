package lab.cherry.nw.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * ClassName : UserRegisterDto
 * Type : class
 * Descrption : 사용자 회원가입을 위한 데이터 객체를 구성하고 있는 클래스입니다.
 * Related : UserServiceImpl
 * </pre>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegisterDto {

    @Schema(title = "사용자 이름", example = "홍길동")
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @Schema(title = "사용자 이메일", example = "test@test.com")
    @NotBlank
    @Email
    @Size(min = 3, max = 40)
    private String email;

    @Schema(title = "사용자 비밀번호", example = "Pa@sW0rd")
    @NotBlank
    @Size(min = 3, max = 40)
    private String password;

    @Schema(title = "사용자 권한 정보", example = "['ROLE_USER']")
    private String[] roles;
}