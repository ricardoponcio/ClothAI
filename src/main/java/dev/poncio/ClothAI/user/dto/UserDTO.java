package dev.poncio.ClothAI.user.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String createdAt;
    private String updatedAt;

}
