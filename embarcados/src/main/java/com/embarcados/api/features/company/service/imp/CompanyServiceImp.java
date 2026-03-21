package com.embarcados.api.features.company.service.imp;

import com.embarcados.api.features.company.domain.CompanyEntity;
import com.embarcados.api.features.company.dto.CompanyResponseDTO;
import com.embarcados.api.features.company.dto.CreateCompanyDTO;
import com.embarcados.api.features.company.dto.UpdateCompanyDTO;
import com.embarcados.api.features.company.repository.CompanyRepository;
import com.embarcados.api.features.company.service.CompanyService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImp implements CompanyService {

    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;

    public CompanyServiceImp(CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
        CompanyEntity existing = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        existing.setName(updateCompanyDTO.getName());
        existing.setEmail(updateCompanyDTO.getEmail());

        if (updateCompanyDTO.getPassword() != null && !updateCompanyDTO.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(updateCompanyDTO.getPassword()));
        }

        CompanyEntity saved = companyRepository.save(existing);
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