package dev.poncio.ClothAI.storage.dto;

import lombok.Data;

@Data
public class StorageDTO {

    private Long id;
    private String serviceEndpoint;
    private String region;
    private String bucketName;
    private String basePrefix;
    private String createdAt;
    private String updatedAt;

}
