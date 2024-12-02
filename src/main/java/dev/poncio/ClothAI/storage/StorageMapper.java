package dev.poncio.ClothAI.storage;

import dev.poncio.ClothAI.storage.dto.CreateStorageDTO;
import dev.poncio.ClothAI.storage.dto.StorageDTO;
import dev.poncio.ClothAI.storage.dto.UpdateStorageDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StorageMapper {

    @Autowired
    private ModelMapper modelMapper;

    public StorageEntity fromDto(CreateStorageDTO dto) {
        return this.modelMapper.map(dto, StorageEntity.class);
    }

    public StorageEntity fromDto(UpdateStorageDTO dto) {
        return this.modelMapper.map(dto, StorageEntity.class);
    }

    public StorageDTO toDto(StorageEntity entity) {
        return this.modelMapper.map(entity, StorageDTO.class);
    }

}
