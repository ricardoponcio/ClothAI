package dev.poncio.ClothAI.clothresource;

import dev.poncio.ClothAI.clothresource.dto.ClothResourceDTO;
import dev.poncio.ClothAI.clothresource.dto.CreateClothResourceDTO;
import dev.poncio.ClothAI.clothresource.dto.UpdateClothResourceDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClothResourceMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ClothResourceEntity fromDto(CreateClothResourceDTO dto) {
        return this.modelMapper.map(dto, ClothResourceEntity.class);
    }

    public ClothResourceEntity fromDto(UpdateClothResourceDTO dto) {
        return this.modelMapper.map(dto, ClothResourceEntity.class);
    }

    public ClothResourceDTO toDto(ClothResourceEntity entity) {
        return this.modelMapper.map(entity, ClothResourceDTO.class);
    }

}
