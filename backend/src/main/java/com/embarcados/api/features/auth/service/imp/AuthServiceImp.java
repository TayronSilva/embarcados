package com.embarcados.api.features.auth.service.imp;

import com.embarcados.api.features.auth.dto.LoginRequestDTO;
import com.embarcados.api.features.auth.dto.LoginResponseDTO;
import com.embarcados.api.features.auth.dto.UserRoleDTO;
import com.embarcados.api.features.auth.exceptions.InvalidCredentialsException;
import com.embarcados.api.features.auth.exceptions.InvalidUserRoleException;
import com.embarcados.api.features.auth.service.AuthService;
import com.embarcados.api.features.company.exceptions.CompanyNotFoundException;
import com.embarcados.api.features.company.repository.CompanyRepository;
import com.embarcados.api.features.driver.exceptions.DriverNotFoundException;
import com.embarcados.api.features.driver.repository.DriverRepository;
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
    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        UserRoleDTO userRole = loginRequestDTO.getUserRole();

        if (userRole == null) {
            throw new InvalidUserRoleException();
        }

        return switch (userRole) {
            case Company -> loginCompany(loginRequestDTO);
            case Driver -> loginDriver(loginRequestDTO);
        };
    }

    private LoginResponseDTO loginCompany(LoginRequestDTO loginRequestDTO) {
        var company = companyRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new CompanyNotFoundException("Empresa não encontrada para o email informado"));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), company.getPassword())) {
            throw new InvalidCredentialsException();
        }

        var now = Instant.now();
        var claims = JwtClaimsSet.builder()
                .issuer("embarcados-api")
                .subject(company.getId())
                .claim("email", company.getEmail())
                .claim("userRole", UserRoleDTO.Company)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(3600))
                .build();

        var token = jwtEncoder.encode(
                JwtEncoderParameters.from(claims)).getTokenValue();

        var response = new LoginResponseDTO();
        response.setUserId(company.getId());
        response.setEmail(company.getEmail());
        response.setUserRole(UserRoleDTO.Company);
        response.setToken(token);

        return response;
    }

    private LoginResponseDTO loginDriver(LoginRequestDTO loginRequestDTO) {
        var driver = driverRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new DriverNotFoundException("Motorista não encontrado para o email informado"));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), driver.getPassword())) {
            throw new InvalidCredentialsException();
        }

        var now = Instant.now();
        var claims = JwtClaimsSet.builder()
                .issuer("embarcados-api")
                .subject(driver.getId())
                .claim("email", driver.getEmail())
                .claim("userRole", UserRoleDTO.Driver)
                .claim("companyId", driver.getCompanyId())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(3600))
                .build();

        var token = jwtEncoder.encode(
                JwtEncoderParameters.from(claims)).getTokenValue();

        var response = new LoginResponseDTO();
        response.setUserId(driver.getId());
        response.setEmail(driver.getEmail());
        response.setUserRole(UserRoleDTO.Driver);
        response.setCompanyId(driver.getCompanyId());
        response.setToken(token);

        return response;
    }
}
