package com.embarcados.api.features.auth.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String userId;
    private String email;
    private String userType; // "company" or "driver"
    private String companyId; // only for drivers
    private String token;

}