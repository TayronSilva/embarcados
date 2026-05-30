package com.embarcados.api.features.trip.service.imp;

import com.embarcados.api.features.driver.domain.DriverEntity;
import com.embarcados.api.features.driver.repository.DriverRepository;
import com.embarcados.api.features.trip.domain.TripEntity;
import com.embarcados.api.features.trip.dto.CreateTripDTO;
import com.embarcados.api.features.trip.dto.TripResponseDTO;
import com.embarcados.api.features.trip.repository.TripRepository;
import com.embarcados.api.features.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripServiceImp implements TripService {

    private final TripRepository tripRepository;
    private final DriverRepository driverRepository;

    @Override
    public TripResponseDTO create(String companyId, CreateTripDTO createTripDTO) {

        DriverEntity driverEntity = driverRepository.findById(createTripDTO.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        if (!driverEntity.getCompanyId().equals(companyId)) {
            throw new RuntimeException("Acess denied: This driver does not belong to this company");
        }

        TripEntity tripEntity = new TripEntity();
        tripEntity.setCompanyId(companyId);
        tripEntity.setDriverId(driverEntity.getId());
        tripEntity.setOrigin(createTripDTO.getOrigin());
        tripEntity.setDestination(createTripDTO.getDestination());
        tripEntity.setStatus("PENDING"); // tudo começa como pendente
        tripEntity.setCreateAt(LocalDateTime.now());

        TripEntity saved = tripRepository.save(tripEntity);
        return toResponse(saved);
    }

    @Override
    public List<TripResponseDTO> findAllByCompany(String companyId) {
        // vai listar apenas as viagens dela mesma
        return tripRepository.findByCompanyId(companyId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public TripResponseDTO startStrip(String tripId, String driverId) {
        TripEntity tripEntity = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        if (!tripEntity.getDriverId().equals(driverId)) {
            throw new RuntimeException("Acess denied: This driver does not belong to this company");
        }

        if (!"PENDING".equals(tripEntity.getStatus())) {
            throw new RuntimeException("Trip is already started");
        }

        tripEntity.setStatus("IN_PROGRESS");
        tripEntity.setStartAt(LocalDateTime.now());

        TripEntity saved = tripRepository.save(tripEntity);
        return toResponse(saved);
    }

    @Override
    public TripResponseDTO endStrip(String tripId, String driverId) {
        TripEntity tripEntity = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        if (!tripEntity.getDriverId().equals(driverId)) {
            throw new RuntimeException("Acess denied: This driver does not belong to this company");
        }

        if (!"IN_PROGRESS".equals(tripEntity.getStatus())) {
            throw new RuntimeException("Only trips that are in progress (IN_PROGRESS) can be completed.");
        }

        tripEntity.setStatus("COMPLETED");
        tripEntity.setEndAt(LocalDateTime.now());

        TripEntity saved = tripRepository.save(tripEntity);
        return toResponse(saved);
    }

    private TripResponseDTO toResponse(TripEntity tripEntity) {
        TripResponseDTO tripResponseDTO = new TripResponseDTO();
        tripResponseDTO.setId(tripEntity.getId());
        tripResponseDTO.setCompanyId(tripEntity.getCompanyId());
        tripResponseDTO.setDriverId(tripEntity.getDriverId());
        tripResponseDTO.setOrigin(tripEntity.getOrigin());
        tripResponseDTO.setDestination(tripEntity.getDestination());
        tripResponseDTO.setStatus(tripEntity.getStatus());
        tripResponseDTO.setCreateAt(tripEntity.getCreateAt());
        tripResponseDTO.setStartAt(tripEntity.getStartAt());
        tripResponseDTO.setEndAt(tripEntity.getEndAt());
        return tripResponseDTO;
    }
}
