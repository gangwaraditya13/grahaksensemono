package com.adish.grahaksensemono.Auth.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDto {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
