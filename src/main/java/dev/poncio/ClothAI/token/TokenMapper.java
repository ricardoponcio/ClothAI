package dev.poncio.ClothAI.token;

import dev.poncio.ClothAI.token.dto.CreateTokenDTO;
import dev.poncio.ClothAI.token.dto.TokenDTO;
import dev.poncio.ClothAI.token.dto.UpdateTokenDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {

    @Autowired
    private ModelMapper modelMapper;

    public TokenEntity fromDto(CreateTokenDTO dto) {
        return this.modelMapper.map(dto, TokenEntity.class);
    }

    public TokenEntity fromDto(UpdateTokenDTO dto) {
        return this.modelMapper.map(dto, TokenEntity.class);
    }

    public TokenDTO toDto(TokenEntity entity) {
        return this.modelMapper.map(entity, TokenDTO.class);
    }

}
