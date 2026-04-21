package com.embarcados.api.driver;

import com.embarcados.api.features.driver.dto.UpdateDriverDTO;
import com.embarcados.api.features.driver.exceptions.DriverNotFoundException;
import com.embarcados.api.features.driver.repository.DriverRepository;
import com.embarcados.api.features.driver.service.imp.DriverServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class DriverTest {

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DriverServiceImp driverService;

    @Test
    void shouldThrowDriverNotFoundExceptionWhenUpdatingMissingDriver() {
        UpdateDriverDTO updateDriverDTO = new UpdateDriverDTO("Motorista X", "x@email.com", "123", "senha123", "company-1");

        Mockito.when(driverRepository.findById("missing-id")).thenReturn(Optional.empty());

        DriverNotFoundException exception = assertThrows(
                DriverNotFoundException.class,
                () -> driverService.update("missing-id", updateDriverDTO));

        assertEquals("Motorista não encontrado", exception.getMessage());
    }
}
