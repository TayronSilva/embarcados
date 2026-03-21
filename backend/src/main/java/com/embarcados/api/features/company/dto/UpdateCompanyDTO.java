package com.embarcados.api.features.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateCompanyDTO {
    private String name;
    private String email;
    private String Document;
}
