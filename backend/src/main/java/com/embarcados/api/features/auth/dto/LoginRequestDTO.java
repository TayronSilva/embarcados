package com.embarcados.api.features.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    @Schema(description = "tipo do usuario que esta logado")
    private UserRoleDTO userRole;
}
