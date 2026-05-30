package com.embarcados.api.features.trip.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TripResponseDTO {
    private String id;
    private String companyId;
    private String driverId;
    private String origin;
    private String destination;
    private String status;
    private LocalDateTime createAt;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
