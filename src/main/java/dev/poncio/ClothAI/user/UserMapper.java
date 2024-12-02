package dev.poncio.ClothAI.user;

import dev.poncio.ClothAI.user.dto.UpdateUserDTO;
import dev.poncio.ClothAI.user.dto.UserDTO;
import dev.poncio.ClothAI.auth.dto.RegisterRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UserEntity fromDto(RegisterRequestDTO dto) {
        return this.modelMapper.map(dto, UserEntity.class);
    }

    public UserEntity fromDto(UpdateUserDTO dto) {
        return this.modelMapper.map(dto, UserEntity.class);
    }

    public UserDTO toDto(UserEntity entity) {
        return this.modelMapper.map(entity, UserDTO.class);
    }

}
