package dev.poncio.ClothAI.storage;

import dev.poncio.ClothAI.common.interfaces.IEntityWithCompany;
import dev.poncio.ClothAI.common.services.CommonService;
import dev.poncio.ClothAI.company.CompanyEntity;
import dev.poncio.ClothAI.storage.dto.CreateStorageDTO;
import dev.poncio.ClothAI.storage.dto.UpdateStorageDTO;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class StorageService extends CommonService<StorageEntity> {
    @Autowired
    private IStorageRepository repository;
    @Autowired
    private StorageMapper mapper;

    @Override
    protected StorageEntity findById(Long id) {
        return this.repository.findByIdAndActiveTrue(id).orElseThrow(EntityNotFoundException::new);
    }

    public StorageEntity get() {
        return this.repository.findOneByCompanyAndActiveTrue(super.getAuthContext().getManagedCompany()).orElse(null);
    }

    public StorageEntity getStorageByCompany(CompanyEntity company) {
        return this.repository.findOneByCompanyAndActiveTrue(company).orElse(null);
    }

    public StorageEntity createNewStorage(CreateStorageDTO createStorageDTO) throws Exception {
        if (get() != null) {
            throw new Exception("Company already has a s3 configuration");
        }
        StorageEntity newStorage = this.mapper.fromDto(createStorageDTO);
        newStorage.setActive(Boolean.TRUE);
        newStorage.setCreatedAt(ZonedDateTime.now());
        newStorage.setCompany(super.getAuthContext().getManagedCompany());
        return this.repository.save(newStorage);
    }

    public StorageEntity updateStorage(Long id, UpdateStorageDTO updateStorageDTO) {
        final var savedToken = this.findByIdSecure(id);
        final var changedToken = mapper.fromDto(updateStorageDTO);
        super.getPartialUpdateMapper().map(changedToken, savedToken);
        return this.repository.save(savedToken);
    }

    public void deleteStorage(Long id) {
        this.findByIdSecure(id);
        this.repository.deleteById(id);
    }

}
