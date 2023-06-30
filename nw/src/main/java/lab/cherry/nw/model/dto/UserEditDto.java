package lab.cherry.nw.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor @AllArgsConstructor
@Data
public class UserEditDto {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Email
    @Size(min = 3, max = 40)
    private String email;

    @NotBlank
    @Size(min = 3, max = 40)
    private String password;

    private String[] roles;
}