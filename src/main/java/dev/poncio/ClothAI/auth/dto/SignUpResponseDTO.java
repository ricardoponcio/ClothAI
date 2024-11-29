package dev.poncio.ClothAI.auth.dto;

import dev.poncio.ClothAI.User.dto.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SignUpResponseDTO extends UserDTO {

    private String token;

}
