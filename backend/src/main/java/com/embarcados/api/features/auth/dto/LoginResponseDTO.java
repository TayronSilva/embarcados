package com.embarcados.api.features.auth.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String userId;
    private String email;
    private UserRoleDTO userRole;
    private String companyId; // only for drivers
    private String token;

}
