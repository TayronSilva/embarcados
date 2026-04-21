package com.embarcados.api.auth;

import com.embarcados.api.features.auth.dto.LoginRequestDTO;
import com.embarcados.api.features.auth.dto.UserRoleDTO;
import com.embarcados.api.features.auth.exceptions.InvalidCredentialsException;
import com.embarcados.api.features.auth.exceptions.InvalidUserRoleException;
import com.embarcados.api.features.auth.service.imp.AuthServiceImp;
import com.embarcados.api.features.company.repository.CompanyRepository;
import com.embarcados.api.features.driver.repository.DriverRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AuthTest {

        @Mock
        private CompanyRepository companyRepository;

        @Mock
        private DriverRepository driverRepository;

        @Mock
        private PasswordEncoder passwordEncoder;

        @Mock
        private JwtEncoder jwtEncoder;

        @InjectMocks
        private AuthServiceImp authService;

        @Test
        void shouldThrowInvalidUserRoleExceptionWhenUserRoleIsMissing() {
                LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
                loginRequestDTO.setUserRole(null);

                InvalidUserRoleException exception = assertThrows(
                                InvalidUserRoleException.class,
                                () -> authService.login(loginRequestDTO));

                assertEquals("Perfil de acesso inválido. Use 'Empresa' ou 'Motorista'", exception.getMessage());
        }

        @Test
        void shouldThrowInvalidCredentialsWhenCompanyPasswordDoesNotMatch() {
                LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
                loginRequestDTO.setEmail("company@email.com");
                loginRequestDTO.setPassword("senha123");
                loginRequestDTO.setUserRole(UserRoleDTO.Company);

                var company = new com.embarcados.api.features.company.domain.CompanyEntity(
                                "1", "Empresa X", "company@email.com", "123", "hash-senha");

                Mockito.when(companyRepository.findByEmail("company@email.com")).thenReturn(Optional.of(company));
                Mockito.when(passwordEncoder.matches("senha123", "hash-senha")).thenReturn(false);

                InvalidCredentialsException exception = assertThrows(
                                InvalidCredentialsException.class,
                                () -> authService.login(loginRequestDTO));

                assertEquals("Email ou senha inválidos", exception.getMessage());
        }

        @Test
        void shouldThrowInvalidCredentialsWhenDriverPasswordDoesNotMatch() {
                LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
                loginRequestDTO.setEmail("driver@email.com");
                loginRequestDTO.setPassword("senha123");
                loginRequestDTO.setUserRole(UserRoleDTO.Driver);

                var driver = new com.embarcados.api.features.driver.domain.DriverEntity(
                                "1", "Motorista X", "driver@email.com", "123", "hash-senha", "company-1");

                Mockito.when(driverRepository.findByEmail("driver@email.com")).thenReturn(Optional.of(driver));
                Mockito.when(passwordEncoder.matches("senha123", "hash-senha")).thenReturn(false);

                InvalidCredentialsException exception = assertThrows(
                                InvalidCredentialsException.class,
                                () -> authService.login(loginRequestDTO));

                assertEquals("Email ou senha inválidos", exception.getMessage());
        }
}
