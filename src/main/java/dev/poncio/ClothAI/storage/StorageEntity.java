package dev.poncio.ClothAI.storage;

import dev.poncio.ClothAI.company.CompanyEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.ZonedDateTime;

@Entity
@Table(schema = "clothai", name = "configuration_storage_s3")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE clothai.configuration_storage_s3 SET active = false WHERE id=?")
public class StorageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    @Column(name = "s3_service_endpoint")
    private String serviceEndpoint;
    @Column(name = "s3_region")
    private String region;
    @Column(name = "s3_access_key")
    private String accessKey;
    @Column(name = "s3_secret_key")
    private String secretKey;
    @Column(name = "s3_bucket_name")
    private String bucketName;
    @Column(name = "base_prefix")
    private String basePrefix;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;
    @Column(name = "active")
    private Boolean active;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

}