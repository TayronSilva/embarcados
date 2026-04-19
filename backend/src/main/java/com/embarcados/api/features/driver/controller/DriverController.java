package com.embarcados.api.features.driver.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@Tag(name = "Drivers", description = "API de gerencimento de Motoristas")
public class DriverController {

    private final DriverService driverService;

    @Operation(summary = "Criar motorista", description = "Cria um novo motorista no sistema")
    @PostMapping()
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Motorista criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<DriverResponseDTO> create(@RequestBody CreateDriverDTO request) {
        DriverResponseDTO driver = driverService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(driver);
    }

    @Operation(summary = "Listar motoristas", description = "Retorna todos os motoristas cadastrados")
    @GetMapping()
    public ResponseEntity<List<DriverResponseDTO>> findAll() {
        List<DriverResponseDTO> drivers = driverService.findAll();

        return ResponseEntity
                .ok(drivers);
    }

    @Operation(summary = "Atualizar motorista", description = "Atualiza os dados de um motorista existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Motorista atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Motorista não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("{id}")
    public ResponseEntity<DriverResponseDTO> update(
            @PathVariable String id,
            @RequestBody UpdateDriverDTO updateDriverDTO) {

        DriverResponseDTO updatedDriver = driverService.update(id, updateDriverDTO);

        return ResponseEntity
                .ok(updatedDriver);
    }

}
