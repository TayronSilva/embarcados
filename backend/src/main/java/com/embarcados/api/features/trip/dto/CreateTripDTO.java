package com.embarcados.api.features.trip.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTripDTO {

    @NotBlank
    private String driverId;

    @NotBlank
    private String origin;

    @NotBlank
    private String destination;
}
