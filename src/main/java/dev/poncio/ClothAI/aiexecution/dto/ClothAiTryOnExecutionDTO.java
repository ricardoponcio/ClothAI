package dev.poncio.ClothAI.aiexecution.dto;

import dev.poncio.ClothAI.company.CompanyEntity;
import dev.poncio.ClothAI.token.TokenEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ClothAiTryOnExecutionDTO {

    private Long id;
    private String createdAt;
    private String startedAt;
    private String finishedAt;
    private Boolean statusResult;
    private String messageResult;
    private String executionIdentification;
    private String outputBase64;

}
