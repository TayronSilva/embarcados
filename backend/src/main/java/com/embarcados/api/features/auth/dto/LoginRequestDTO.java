package com.embarcados.api.features.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String userType; // "company" or "driver"
}
