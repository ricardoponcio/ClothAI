package dev.poncio.ClothAI.company;

import dev.poncio.ClothAI.auth.AuthContext;
import dev.poncio.ClothAI.company.dto.CreateCompanyDTO;
import dev.poncio.ClothAI.company.dto.UpdateCompanyDTO;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Collections;
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

    @Autowired
    private AuthContext authContext;

    public CompanyEntity findById(Long id) {
        return this.repository.findByIdAndActiveTrue(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<CompanyEntity> listCompanies() {
        return this.repository.findByUserId(this.authContext.getLoggedUserId());
    }

    public CompanyEntity createCompany(CreateCompanyDTO createCompanyDTO) {
        final var newCompany = this.mapper.fromDto(createCompanyDTO);
        newCompany.setCreatedAt(ZonedDateTime.now());
        newCompany.setActive(Boolean.TRUE);
        newCompany.setUsers(Collections.singletonList(this.authContext.getLoggedUser()));
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
