package dev.poncio.ClothAI.aiexecution.dto;

import dev.poncio.ClothAI.company.CompanyEntity;
import dev.poncio.ClothAI.token.TokenEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class StartClothAiTryOnExecutionDTO {

    private String clothResourceIdentification;

}
