package com.embarcados.api.features.auth.service;

import com.embarcados.api.features.auth.dto.LoginRequestDTO;
import com.embarcados.api.features.auth.dto.LoginResponseDTO;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}
