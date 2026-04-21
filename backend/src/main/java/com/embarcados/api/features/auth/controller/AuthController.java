package com.embarcados.api.features.auth.controller;

import com.embarcados.api.features.auth.dto.LoginRequestDTO;
import com.embarcados.api.features.auth.dto.LoginResponseDTO;
import com.embarcados.api.features.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "API de gerencimento de Empresas")
public class AuthController {

    private final AuthService authService;


    @Operation(summary = "Auth Empresa/Driver", description = "API de autenticação para empresas e motoristas")
    @PostMapping()
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticação com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }

}
