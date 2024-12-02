package dev.poncio.ClothAI.storage;

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
public class StorageService {
    @Autowired
    private IStorageRepository repository;
    @Autowired
    private StorageMapper mapper;
    @Autowired
    @Qualifier("partialUpdateMapper")
    private ModelMapper partialUpdateMapper;

    public StorageEntity findById(Long id) {
        return this.repository.findByIdAndActiveTrue(id).orElseThrow(EntityNotFoundException::new);
    }

    public StorageEntity get(CompanyEntity company) {
        return this.repository.findOneByCompanyAndActiveTrue(company).orElse(null);
    }

    public StorageEntity createNewStorage(CompanyEntity company, CreateStorageDTO createStorageDTO) throws Exception {
        if (get(company) != null) {
            throw new Exception("Company already has a s3 configuration");
        }
        StorageEntity newStorage = this.mapper.fromDto(createStorageDTO);
        newStorage.setActive(Boolean.TRUE);
        newStorage.setCreatedAt(ZonedDateTime.now());
        newStorage.setCompany(company);
        return this.repository.save(newStorage);
    }

    public StorageEntity updateStorage(Long id, UpdateStorageDTO updateStorageDTO) {
        final var savedToken = this.findById(id);
        final var changedToken = mapper.fromDto(updateStorageDTO);
        partialUpdateMapper.map(changedToken, savedToken);
        return this.repository.save(savedToken);
    }

    public void deleteStorage(Long id) {
        this.findById(id);
        this.repository.deleteById(id);
    }


}
