package dev.poncio.ClothAI.storage.dto;

import dev.poncio.ClothAI.company.CompanyEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class CreateStorageDTO {

    private String serviceEndpoint;
    private String region;
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String basePrefix;

}
