package com.embarcados.api.features.company.repository;

import com.embarcados.api.features.company.domain.CompanyEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CompanyRepository extends MongoRepository<CompanyEntity, String> {
    Optional<CompanyEntity> findById(String id);
    Optional<CompanyEntity> findByEmail(String email);
}
