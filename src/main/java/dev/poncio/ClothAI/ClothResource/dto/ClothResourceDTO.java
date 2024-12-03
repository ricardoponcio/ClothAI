package dev.poncio.ClothAI.ClothResource.dto;

import dev.poncio.ClothAI.company.CompanyEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ClothResourceDTO {

    private Long id;
    private String name;
    private String identification;
    private String createdAt;
    private String updatedAt;

}
