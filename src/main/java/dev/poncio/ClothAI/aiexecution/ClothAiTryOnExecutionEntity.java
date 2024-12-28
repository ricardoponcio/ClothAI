package dev.poncio.ClothAI.aiexecution;

import dev.poncio.ClothAI.clothresource.ClothResourceEntity;
import dev.poncio.ClothAI.common.interfaces.IEntityWithCompany;
import dev.poncio.ClothAI.company.CompanyEntity;
import dev.poncio.ClothAI.token.TokenEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(schema = "clothai", name = "cloth_ai_try_on_execution")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClothAiTryOnExecutionEntity implements IEntityWithCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @Column(name = "execution_started_at")
    private ZonedDateTime startedAt;
    @Column(name = "execution_finished_at")
    private ZonedDateTime finishedAt;
    @Column(name = "status_result")
    private Boolean statusResult;
    @Column(name = "message_result")
    private String messageResult;
    @Column(name = "input_url")
    private String inputUrl;
    @Column(name = "output_url")
    private String outputUrl;
    @Column(name = "execution_identification")
    private String executionIdentification;
    @ManyToOne
    @JoinColumn(name = "cloth_resource_id")
    private ClothResourceEntity clothResource;
    @ManyToOne
    @JoinColumn(name = "token_id")
    private TokenEntity token;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

}