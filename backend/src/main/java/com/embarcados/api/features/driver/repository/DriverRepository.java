package com.embarcados.api.features.driver.repository;

import com.embarcados.api.features.driver.domain.DriverEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DriverRepository extends MongoRepository<DriverEntity, String> {
    Optional<DriverEntity> findById(String id);

    Optional<DriverEntity> findByEmail(String email);
}
