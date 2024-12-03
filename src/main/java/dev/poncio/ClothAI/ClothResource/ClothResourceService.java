package dev.poncio.ClothAI.ClothResource;

import dev.poncio.ClothAI.ClothResource.dto.CreateClothResourceDTO;
import dev.poncio.ClothAI.ClothResource.dto.UpdateClothResourceDTO;
import dev.poncio.ClothAI.common.services.CommonService;
import dev.poncio.ClothAI.company.CompanyEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ClothResourceService extends CommonService {

    @Autowired
    private IClothResourceRepository repository;

    @Autowired
    private ClothResourceMapper mapper;

    public ClothResourceEntity findById(Long id) {
        return this.repository.findByIdAndActiveTrue(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<ClothResourceEntity> listClothResources(CompanyEntity company) {
        return this.repository.findAllByCompanyAndActiveTrue(company);
    }

    public ClothResourceEntity createClothResource(CompanyEntity company, CreateClothResourceDTO createClothResourceDTO, MultipartFile attach) throws IOException {
        final var newClothResource = this.mapper.fromDto(createClothResourceDTO);
        newClothResource.setCreatedAt(ZonedDateTime.now());
        newClothResource.setActive(Boolean.TRUE);
        newClothResource.setCompany(company);
        if (newClothResource.getIdentification() == null)
            newClothResource.setIdentification(UUID.randomUUID().toString());
        final var uploadResponse = super.getS3StorageService().uploadFile(company, newClothResource.getIdentification(), attach.getInputStream());
        newClothResource.setUrl(uploadResponse);
        return this.repository.save(newClothResource);
    }

    public ClothResourceEntity updateClothResource(Long id, UpdateClothResourceDTO updateClothResourceDTO) {
        final var savedClothResource = this.findById(id);
        final var changedClothResource = this.mapper.fromDto(updateClothResourceDTO);
        super.getPartialUpdateMapper().map(changedClothResource, savedClothResource);
        return this.repository.save(savedClothResource);
    }

    public void deleteClothResource(Long id) {
        this.findById(id);
        this.repository.deleteById(id);
    }

}
