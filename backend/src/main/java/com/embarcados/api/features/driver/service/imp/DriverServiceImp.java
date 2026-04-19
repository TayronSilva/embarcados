package com.embarcados.api.features.driver.service.imp;

import com.embarcados.api.features.driver.domain.DriverEntity;
import com.embarcados.api.features.driver.dto.CreateDriverDTO;
import com.embarcados.api.features.driver.dto.DriverResponseDTO;
import com.embarcados.api.features.driver.dto.UpdateDriverDTO;
import com.embarcados.api.features.driver.repository.DriverRepository;
import com.embarcados.api.features.driver.service.DriverService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImp implements DriverService {

    private final PasswordEncoder passwordEncoder;
    private final DriverRepository driverRepository;

    @Override
    public DriverResponseDTO create(CreateDriverDTO createDriverDTO) {
        DriverEntity driverEntity = new DriverEntity();
        driverEntity.setName(createDriverDTO.getName());
        driverEntity.setEmail(createDriverDTO.getEmail());
        driverEntity.setDocument(createDriverDTO.getDocument());
        driverEntity.setCompanyId(createDriverDTO.getCompanyId());

        String passwordHash = passwordEncoder.encode(createDriverDTO.getPassword());
        driverEntity.setPassword(passwordHash);

        DriverEntity saved = driverRepository.save(driverEntity);
        return toResponse(saved);
    }

    @Override
    public List<DriverResponseDTO> findAll() {
        List<DriverEntity> drivers = driverRepository.findAll();

        return drivers.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public DriverResponseDTO update(String id, UpdateDriverDTO updateDriverDTO) {
        DriverEntity driver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Motorista não encontrado"));

        driver.setName(updateDriverDTO.getName());
        driver.setEmail(updateDriverDTO.getEmail());
        driver.setDocument(updateDriverDTO.getDocument());
        driver.setPassword(passwordEncoder.encode(updateDriverDTO.getPassword()));
        driver.setCompanyId(updateDriverDTO.getCompanyId());

        DriverEntity saved = driverRepository.save(driver);
        return toResponse(saved);
    }

    private DriverResponseDTO toResponse(DriverEntity driverEntity) {
        DriverResponseDTO driverResponseDTO = new DriverResponseDTO();
        driverResponseDTO.setId(driverEntity.getId());
        driverResponseDTO.setName(driverEntity.getName());
        driverResponseDTO.setEmail(driverEntity.getEmail());
        driverResponseDTO.setDocument(driverEntity.getDocument());
        driverResponseDTO.setCompanyId(driverEntity.getCompanyId());
        return driverResponseDTO;
    }
}
