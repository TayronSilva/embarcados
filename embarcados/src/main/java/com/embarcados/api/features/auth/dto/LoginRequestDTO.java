package com.embarcados.api.features.auth.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String email;
    private String password;

}
