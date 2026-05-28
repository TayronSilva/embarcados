package com.embarcados.api.features.driver.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.embarcados.api.features.driver.dto.DriverResponseDTO;
import com.embarcados.api.features.driver.dto.CreateDriverDTO;
import com.embarcados.api.features.driver.dto.UpdateDriverDTO;
import com.embarcados.api.features.driver.service.DriverService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
@Tag(name = "Drivers", description = "API de gerenciamento de Motoristas")
public class DriverController {

    private final DriverService driverService;

    @Operation(summary = "Criar motorista", description = "Cria um novo motorista associado à empresa autenticada")
    @PostMapping()
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Motorista criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<DriverResponseDTO> create(
            @RequestBody CreateDriverDTO createDriverDTO,
            @Parameter(hidden = true) Authentication authentication) {

        // extrai o ID da empresa logada de dentro do Token JWT
        String companyId = authentication.getName();

        DriverResponseDTO driver = driverService.create(companyId, createDriverDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(driver); // RETORNO ADICIONADO AQUI
    }

    @Operation(summary = "Listar motoristas", description = "Retorna todos os motoristas cadastrados")
    @GetMapping()
    public ResponseEntity<List<DriverResponseDTO>> findAll() {
        List<DriverResponseDTO> drivers = driverService.findAll();

        return ResponseEntity.ok(drivers);
    }

    @Operation(summary = "Atualizar motorista", description = "Atualiza os dados de um motorista existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Motorista updated com sucesso"),
            @ApiResponse(responseCode = "404", description = "Motorista não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("{id}")
    public ResponseEntity<DriverResponseDTO> update(
            @PathVariable String id,
            @RequestBody UpdateDriverDTO updateDriverDTO,
            @Parameter(hidden = true) Authentication authentication) { // Adicionado o token no PUT também

        String companyId = authentication.getName();

        // passa o id do motorista e o id da empresa dona para validar no service
        DriverResponseDTO updatedDriver = driverService.update(id, companyId, updateDriverDTO);

        return ResponseEntity.ok(updatedDriver);
    }
}