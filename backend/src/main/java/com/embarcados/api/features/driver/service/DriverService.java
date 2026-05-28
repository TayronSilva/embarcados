package com.embarcados.api.features.driver.service;

import com.embarcados.api.features.driver.dto.CreateDriverDTO;
import com.embarcados.api.features.driver.dto.DriverResponseDTO;
import com.embarcados.api.features.driver.dto.UpdateDriverDTO;

import java.util.List;

public interface DriverService {

    DriverResponseDTO create(String companyId, CreateDriverDTO createDriverDTO);

    List<DriverResponseDTO> findAll();

    DriverResponseDTO update(String id, String companyId, UpdateDriverDTO updateDriverDTO);
}
