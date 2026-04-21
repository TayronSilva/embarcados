package com.embarcados.api.features.company.service.imp;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.embarcados.api.features.company.domain.CompanyEntity;
import com.embarcados.api.features.company.dto.CompanyResponseDTO;
import com.embarcados.api.features.company.dto.CreateCompanyDTO;
import com.embarcados.api.features.company.dto.UpdateCompanyDTO;
import com.embarcados.api.features.company.exceptions.CompanyNotFoundException;
import com.embarcados.api.features.company.repository.CompanyRepository;
import com.embarcados.api.features.company.service.CompanyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImp implements CompanyService {

    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;

    @Override
    public CompanyResponseDTO create(CreateCompanyDTO createCompanyDTO) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setName(createCompanyDTO.getName());
        companyEntity.setEmail(createCompanyDTO.getEmail());
        companyEntity.setDocument(createCompanyDTO.getDocument());

        String passwordHash = passwordEncoder.encode(createCompanyDTO.getPassword());
        companyEntity.setPassword(passwordHash);

        CompanyEntity saved = companyRepository.save(companyEntity);
        return toResponse(saved);
    }

    @Override
    public List<CompanyResponseDTO> findAll() {
        List<CompanyEntity> companies = companyRepository.findAll();

        return companies.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CompanyResponseDTO update(String id, UpdateCompanyDTO updateCompanyDTO) {
        CompanyEntity company = companyRepository.findById(id)
                .orElseThrow(CompanyNotFoundException::new);

        company.setName(updateCompanyDTO.getName());
        company.setEmail(updateCompanyDTO.getEmail());
        company.setDocument(updateCompanyDTO.getDocument());

        CompanyEntity saved = companyRepository.save(company);
        return toResponse(saved);
    }

    private CompanyResponseDTO toResponse(CompanyEntity companyEntity) {
        CompanyResponseDTO companyResponseDTO = new CompanyResponseDTO();
        companyResponseDTO.setId(companyEntity.getId());
        companyResponseDTO.setName(companyEntity.getName());
        companyResponseDTO.setEmail(companyEntity.getEmail());
        companyResponseDTO.setDocument(companyEntity.getDocument());
        return companyResponseDTO;
    }
}
