package dev.poncio.ClothAI.token.dto;

import dev.poncio.ClothAI.company.CompanyEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class TokenDTO {

    private Long id;
    private String description;
    private String token;
    private String createdAt;
    private String expiresAt;

}
