package com.embarcados.api.features.trip.repository;

import com.embarcados.api.features.trip.domain.TripEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TripRepository extends MongoRepository<TripEntity, String> {

    //vai buscar toda as viagens de uma determinada empresa
    List<TripEntity> findByCompanyId(String companyId);

    // vai buscar viagens ativas de um motorista especifico
    List<TripEntity> findByDriverIdAndStatus(String driverId, String status);
}
