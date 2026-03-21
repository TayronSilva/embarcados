package com.embarcados.api.features.company.controller;

import com.embarcados.api.features.company.dto.CompanyResponseDTO;
import com.embarcados.api.features.company.dto.CreateCompanyDTO;
import com.embarcados.api.features.company.dto.UpdateCompanyDTO;
import com.embarcados.api.features.company.service.CompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@Tag(name = "CompanyController")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("create")
    public CompanyResponseDTO create(@RequestBody CreateCompanyDTO createCompanyDTO) {
        return companyService.create(createCompanyDTO);
    }

    @GetMapping("/findAll")
    public Iterable<CompanyResponseDTO> findAll() {
        return companyService.findAll();
    }

    @PutMapping("/update/{id}")
    public CompanyResponseDTO update(
            @PathVariable String id,
            @RequestBody UpdateCompanyDTO updateCompanyDTO) {

        return companyService.update(id, updateCompanyDTO);
    }

}
