package com.embarcados.api.company;

import com.embarcados.api.features.company.domain.CompanyEntity;
import com.embarcados.api.features.company.dto.CompanyResponseDTO;
import com.embarcados.api.features.company.dto.CreateCompanyDTO;
import com.embarcados.api.features.company.dto.UpdateCompanyDTO;
import com.embarcados.api.features.company.exceptions.CompanyNotFoundException;
import com.embarcados.api.features.company.repository.CompanyRepository;
import com.embarcados.api.features.company.service.imp.CompanyServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CompanyTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CompanyServiceImp companyService;

    @Test
    void shouldCreateCompanySuccessfully() {
        CreateCompanyDTO inputDto = new CreateCompanyDTO("Empresa X", "x@email.com", "123", "senha123");
        CompanyEntity entitySalva = new CompanyEntity("id-gerado", "Empresa X", "x@email.com", "123", "hash-senha");

        Mockito.when(passwordEncoder.encode(any())).thenReturn("hash-senha");
        Mockito.when(companyRepository.save(any(CompanyEntity.class))).thenReturn(entitySalva);

        CompanyResponseDTO result = companyService.create(inputDto);

        assertEquals("Empresa X", result.getName());
        assertEquals("id-gerado", result.getId());

        Mockito.verify(companyRepository, Mockito.times(1)).save(any(CompanyEntity.class));
    }

    @Test
    void findAllCompaniesSucess() {
        CompanyEntity company1 = new CompanyEntity("1", "Company A", "a@test.com", "111", "pass1");
        CompanyEntity company2 = new CompanyEntity("2", "Company B", "b@test.com", "222", "pass2");

        List<CompanyEntity> companies = List.of(company1, company2);

        Mockito.when(companyRepository.findAll()).thenReturn(companies);

        List<CompanyResponseDTO> result = companyService.findAll();

        assertEquals(2, result.size());

        Mockito.verify(companyRepository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldThrowCompanyNotFoundExceptionWhenUpdatingMissingCompany() {
        UpdateCompanyDTO updateCompanyDTO = new UpdateCompanyDTO("Empresa X", "x@email.com", "123");

        Mockito.when(companyRepository.findById("missing-id")).thenReturn(Optional.empty());

        CompanyNotFoundException exception = assertThrows(
                CompanyNotFoundException.class,
                () -> companyService.update("missing-id", updateCompanyDTO));

        assertEquals("Empresa não encontrada", exception.getMessage());
    }
}
