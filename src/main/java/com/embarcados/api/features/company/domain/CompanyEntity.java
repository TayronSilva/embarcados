package com.embarcados.api.features.company.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "company")
@AllArgsConstructor
public class CompanyEntity {

    @Id
    private String id;
    private String name;
    private String email;
    private String document;
    private String password;


    public CompanyEntity() {

    }
}
