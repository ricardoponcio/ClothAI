package dev.poncio.ClothAI.aiexecution;

import dev.poncio.ClothAI.aiexecution.dto.ClothAiTryOnExecutionDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClothAiTryOnExecutionMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ClothAiTryOnExecutionDTO toDto(ClothAiTryOnExecutionEntity entity) {
        return this.modelMapper.map(entity, ClothAiTryOnExecutionDTO.class);
    }

}
