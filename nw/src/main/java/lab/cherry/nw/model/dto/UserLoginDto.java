package lab.cherry.nw.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * ClassName : UserEditDto
 * Type : class
 * Descrption : 사용자 수정을 위한 데이터 객체를 구성하고 있는 클래스입니다.
 * Related : UserServiceImpl
 * </pre>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginDto {

    @Schema(title = "사용자 이름", example = "홍길동")
    @NotBlank
    private String username;

    @Schema(title = "사용자 비밀번호", example = "Pa@sW0rd")
    @NotBlank
    private String password;
}