package com.embarcados.api.features.company.controller;

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

import com.embarcados.api.features.company.dto.CompanyResponseDTO;
import com.embarcados.api.features.company.dto.CreateCompanyDTO;
import com.embarcados.api.features.company.dto.UpdateCompanyDTO;
import com.embarcados.api.features.company.service.CompanyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
@Tag(name = "Companies", description = "API de gerencimento de Empresas")
public class CompanyController {

    private final CompanyService companyService;

    @Operation(summary = "Criar empresa", description = "Cria uma nova empresa no sistema")
    @PostMapping()
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empresa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })

    public ResponseEntity<CompanyResponseDTO> create(@RequestBody CreateCompanyDTO request) {
        CompanyResponseDTO company = companyService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(company);
    }

    @Operation(summary = "Listar empresas", description = "Retorna todas as empresas cadastradas")
    @GetMapping()
    public ResponseEntity<List<CompanyResponseDTO>> findAll() {
        List<CompanyResponseDTO> companies = companyService.findAll();

        return ResponseEntity
                .ok(companies);
    }

    @Operation(summary = "Atualizar empresa", description = "Atualiza os dados de uma empresa existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("{id}")
    public ResponseEntity<CompanyResponseDTO> update(
            @PathVariable String id,
            @RequestBody UpdateCompanyDTO updateCompanyDTO) {

        CompanyResponseDTO updatedCompany = companyService.update(id, updateCompanyDTO);

        return ResponseEntity
                .ok(updatedCompany);
    }

}
