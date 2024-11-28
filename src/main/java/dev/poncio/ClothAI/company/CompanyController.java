package dev.poncio.ClothAI.company;

import dev.poncio.ClothAI.company.dto.CompanyDTO;
import dev.poncio.ClothAI.company.dto.CreateCompanyDTO;
import dev.poncio.ClothAI.company.dto.UpdateCompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyMapper mapper;

    @Autowired
    private CompanyService service;

    @GetMapping(value = "/list")
    public List<CompanyDTO> listCompanies() {
        return this.service.listCompanies().stream().map(this.mapper::toDto).collect(Collectors.toList());
    }

    @PutMapping(value = "/create")
    public CompanyDTO createCompany(@RequestBody CreateCompanyDTO createCompanyDTO) {
        return this.mapper.toDto(this.service.createCompany(createCompanyDTO));
    }

    @PatchMapping(value = "/update/{id}")
    public CompanyDTO updateCompany(@PathVariable Long id, @RequestBody UpdateCompanyDTO updateCompanyDTO) {
        return this.mapper.toDto(this.service.updateCompany(id, updateCompanyDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        this.service.deleteCompany(id);
        return ResponseEntity.ok().build();
    }

}