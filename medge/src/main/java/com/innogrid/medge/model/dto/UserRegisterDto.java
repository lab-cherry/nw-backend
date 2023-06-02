package com.innogrid.medge.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@NoArgsConstructor @AllArgsConstructor
@Data
public class UserRegisterDto {
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