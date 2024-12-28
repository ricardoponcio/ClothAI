package dev.poncio.ClothAI.clothresource;

import dev.poncio.ClothAI.clothresource.dto.CreateClothResourceDTO;
import dev.poncio.ClothAI.clothresource.dto.UpdateClothResourceDTO;
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
public class ClothResourceService extends CommonService<ClothResourceEntity> {

    @Autowired
    private IClothResourceRepository repository;

    @Autowired
    private ClothResourceMapper mapper;

    @Override
    protected ClothResourceEntity findById(Long id) {
        return this.repository.findByIdAndActiveTrue(id).orElseThrow(EntityNotFoundException::new);
    }

    public ClothResourceEntity findByClothResourceIdentification(String clothResourceIdentification) {
        ClothResourceEntity clothResource = this.repository.findByIdentificationAndActiveTrue(clothResourceIdentification).orElseThrow(EntityNotFoundException::new);
        super.checkSecurityEntity(clothResource);
        return clothResource;
    }

    public List<ClothResourceEntity> listClothResources() {
        return this.repository.findAllByCompanyAndActiveTrue(super.getAuthContext().getManagedCompany());
    }

    public ClothResourceEntity createClothResource(CreateClothResourceDTO createClothResourceDTO, MultipartFile attach) throws IOException {
        final var newClothResource = this.mapper.fromDto(createClothResourceDTO);
        newClothResource.setCreatedAt(ZonedDateTime.now());
        newClothResource.setActive(Boolean.TRUE);
        newClothResource.setCompany(super.getAuthContext().getManagedCompany());
        if (newClothResource.getIdentification() == null)
            newClothResource.setIdentification(UUID.randomUUID().toString());
        final var uploadResponse = super.getS3StorageService().uploadFile(super.getAuthContext().getManagedCompany(),
                newClothResource.getIdentification(), attach.getInputStream());
        newClothResource.setUrl(uploadResponse);
        return this.repository.save(newClothResource);
    }

    public ClothResourceEntity updateClothResource(Long id, UpdateClothResourceDTO updateClothResourceDTO) {
        final var savedClothResource = this.findByIdSecure(id);
        final var changedClothResource = this.mapper.fromDto(updateClothResourceDTO);
        super.getPartialUpdateMapper().map(changedClothResource, savedClothResource);
        return this.repository.save(savedClothResource);
    }

    public void deleteClothResource(Long id) {
        this.findByIdSecure(id);
        this.repository.deleteById(id);
    }

}
