package dev.poncio.ClothAI.company;

import dev.poncio.ClothAI.company.dto.CreateCompanyDTO;
import dev.poncio.ClothAI.company.dto.UpdateCompanyDTO;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private ICompanyRepository repository;

    @Autowired
    private CompanyMapper mapper;

    @Autowired
    @Qualifier("partialUpdateMapper")
    private ModelMapper partialUpdateMapper;

    public CompanyEntity findById(Long id) {
        return this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<CompanyEntity> listCompanies() {
        return this.repository.findAllByActiveTrue();
    }

    public CompanyEntity createCompany(CreateCompanyDTO createCompanyDTO) {
        final var newCompany = this.mapper.fromDto(createCompanyDTO);
        newCompany.setCreatedAt(ZonedDateTime.now());
        newCompany.setActive(Boolean.TRUE);
        return this.repository.save(newCompany);
    }

    public CompanyEntity updateCompany(Long id, UpdateCompanyDTO updateCompanyDTO) {
        final var savedCompany = this.findById(id);
        final var changedCompany = mapper.fromDto(updateCompanyDTO);
        partialUpdateMapper.map(changedCompany, savedCompany);
        return this.repository.save(savedCompany);
    }

    public void deleteCompany(Long id) {
        this.findById(id);
        this.repository.deleteById(id);
    }

}
