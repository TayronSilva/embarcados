package com.embarcados.api.features.trip.service;

import com.embarcados.api.features.trip.dto.CreateTripDTO;
import com.embarcados.api.features.trip.dto.TripResponseDTO;

import java.util.List;

public interface TripService {

    TripResponseDTO create(String companyId, CreateTripDTO createTripDTO);

    List<TripResponseDTO> findAllByCompany(String companyId);

    TripResponseDTO startStrip(String tripId, String driverId);

    TripResponseDTO endStrip(String tripId, String driverId);
}
