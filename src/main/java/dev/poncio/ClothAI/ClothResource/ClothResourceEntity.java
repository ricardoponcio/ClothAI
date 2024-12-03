package dev.poncio.ClothAI.ClothResource;

import dev.poncio.ClothAI.company.CompanyEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.ZonedDateTime;

@Entity
@Table(schema = "clothai", name = "cloth_resource")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE clothai.cloth_resource SET active = false WHERE id=?")
public class ClothResourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "identification")
    private String identification;
    @Column(name = "url")
    private String url;
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