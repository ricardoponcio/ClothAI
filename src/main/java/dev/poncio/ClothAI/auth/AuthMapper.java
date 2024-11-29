package dev.poncio.ClothAI.auth;

import dev.poncio.ClothAI.User.UserEntity;
import dev.poncio.ClothAI.auth.dto.RegisterResponseDTO;
import dev.poncio.ClothAI.auth.dto.SignUpResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    @Autowired
    private ModelMapper modelMapper;

    public SignUpResponseDTO map(UserEntity user) {
        return this.modelMapper.map(user, SignUpResponseDTO.class);
    }

    public SignUpResponseDTO map(UserEntity user, String token) {
        SignUpResponseDTO response = this.modelMapper.map(user, SignUpResponseDTO.class);
        response.setToken(token);
        return response;
    }

    public RegisterResponseDTO afterRegister(UserEntity entity) {
        return this.modelMapper.map(entity, RegisterResponseDTO.class);
    }

}
