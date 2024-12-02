package dev.poncio.ClothAI.token;

import dev.poncio.ClothAI.company.CompanyEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(schema = "clothai", name = "company_token")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE clothai.company_token SET active = false WHERE id=?")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "token")
    private String token;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @Column(name = "expires_at")
    private ZonedDateTime expiresAt;
    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;
    @Column(name = "active")
    private Boolean active;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

}