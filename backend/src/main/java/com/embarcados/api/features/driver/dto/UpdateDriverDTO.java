package com.embarcados.api.features.driver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateDriverDTO {

    private String name;
    private String email;
    private String document;
    private String password;
    private String companyId;
}
