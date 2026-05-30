package com.embarcados.api.features.trip.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "trips")
@NoArgsConstructor
@AllArgsConstructor
public class TripEntity {

    @Id
    private String id;
    private String companyId; // vincula a empresa dona da frota
    private String driverId; // vincula ao motorista que vai dirigir

    private String origin; // ex: "Sao Paulo, SP" ou coordenadas
    private String destination; // ex: "Rio de Janeiro, RJ"

    private String status; // PENDING, IN_PROGRESS, COMPLETED, CANCELED

    private LocalDateTime createAt;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
