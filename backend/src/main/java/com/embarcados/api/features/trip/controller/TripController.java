package com.embarcados.api.features.trip.controller;

import com.embarcados.api.features.trip.dto.CreateTripDTO;
import com.embarcados.api.features.trip.dto.TripResponseDTO;
import com.embarcados.api.features.trip.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;  // ← Adicionar
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;  // ← Adicionar
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
@Tag(name = "Trips", description = "API de gerenciamento de Viagens e Monitoramento")
public class TripController {

    private final TripService tripService;

    @PostMapping
    @Operation(summary = "Criar viagem", description = "Cria uma nova viagem pendente vinculando um motorista da empresa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Viagem criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou motorista não pertence à empresa"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<TripResponseDTO> create(
            @RequestBody @Valid CreateTripDTO createTripDTO,
            @Parameter(hidden = true) Authentication authentication) {

        String companyId = authentication.getName();
        TripResponseDTO response = tripService.create(companyId, createTripDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar viagens da empresa", description = "Retorna todas as viagens cadastradas pela empresa autenticada")
    @ApiResponse(responseCode = "200", description = "Viagens listadas com sucesso")
    public ResponseEntity<List<TripResponseDTO>> findAll(
            @Parameter(hidden = true) Authentication authentication) {

        String companyId = authentication.getName();
        List<TripResponseDTO> trips = tripService.findAllByCompany(companyId);

        return ResponseEntity.ok(trips);
    }

    @Operation(summary = "Iniciar viagem (Motorista)", description = "Alterar o status da viagem para IN_PROGRESS. Requer token do Motorista.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Viagem iniciada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Viagem não está PENDING"),
            @ApiResponse(responseCode = "403", description = "Não autorizado - motorista inválido ou viagem já iniciada")
    })
    @PutMapping("/{id}/start")
    public ResponseEntity<TripResponseDTO> startTrip(
            @PathVariable("id") String tripId,
            @Parameter(hidden = true) Authentication authentication) {

        String driverId = authentication.getName();
        TripResponseDTO response = tripService.startStrip(tripId, driverId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Finalizar viagem (Motorista)", description = "Alterar o status da viagem para COMPLETED. Requer token do Motorista.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Viagem finalizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Viagem não está IN_PROGRESS"),
            @ApiResponse(responseCode = "403", description = "Não autorizado - motorista inválido")
    })
    @PutMapping("/{id}/end")
    public ResponseEntity<TripResponseDTO> endTrip(
            @PathVariable("id") String tripId,
            @Parameter(hidden = true) Authentication authentication) {

        String driverId = authentication.getName();
        TripResponseDTO response = tripService.endStrip(tripId, driverId);

        return ResponseEntity.ok(response);
    }
}