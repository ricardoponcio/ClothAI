package dev.poncio.ClothAI.company;

import dev.poncio.ClothAI.company.dto.CompanyDTO;
import dev.poncio.ClothAI.company.dto.CreateCompanyDTO;
import dev.poncio.ClothAI.company.dto.UpdateCompanyDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CompanyEntity fromDto(CreateCompanyDTO dto) {
        return this.modelMapper.map(dto, CompanyEntity.class);
    }

    public CompanyEntity fromDto(UpdateCompanyDTO dto) {
        return this.modelMapper.map(dto, CompanyEntity.class);
    }

    public CompanyDTO toDto(CompanyEntity entity) {
        return this.modelMapper.map(entity, CompanyDTO.class);
    }

}
