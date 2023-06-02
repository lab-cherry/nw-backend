package com.innogrid.medge.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}