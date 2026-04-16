package com.embarcados.api.features.auth.service.imp;

import com.embarcados.api.features.auth.dto.LoginRequestDTO;
import com.embarcados.api.features.auth.dto.LoginResponseDTO;
import com.embarcados.api.features.auth.service.AuthService;
import com.embarcados.api.features.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        var company = companyRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), company.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        var now = Instant.now();
        var claims = JwtClaimsSet.builder()
                .issuer("embarcados-api")
                .subject(company.getId().toString())
                .claim("email", company.getEmail())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(3600))
                .build();

        var token = jwtEncoder.encode(
                JwtEncoderParameters.from(claims)
        ).getTokenValue();


        var response = new LoginResponseDTO();
        response.setCompanyId(company.getId().toString());
        response.setEmail(company.getEmail());
        response.setToken(token);

        return response;
    }
}
