package dev.poncio.ClothAI.auth.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {

    private String name;
    private String email;
    private String password;
    private String confirmPassword;

}
