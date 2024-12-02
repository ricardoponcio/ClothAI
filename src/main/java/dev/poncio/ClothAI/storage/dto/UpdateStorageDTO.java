package dev.poncio.ClothAI.storage.dto;

import lombok.Data;

@Data
public class UpdateStorageDTO {

    private String serviceEndpoint;
    private String region;
    private String bucketName;
    private String basePrefix;
    
}
