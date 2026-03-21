package com.embarcados.api.features.company.service;

import com.embarcados.api.features.company.dto.CompanyResponseDTO;
import com.embarcados.api.features.company.dto.CreateCompanyDTO;
import com.embarcados.api.features.company.dto.UpdateCompanyDTO;

import java.util.List;

public interface CompanyService {

    CompanyResponseDTO create(CreateCompanyDTO createCompanyDTO);

    List<CompanyResponseDTO> findAll();

    CompanyResponseDTO update(String id, UpdateCompanyDTO updateCompanyDTO);
}