package com.embarcados.api.features.auth.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String companyId;
    private String email;
    private String token;

}