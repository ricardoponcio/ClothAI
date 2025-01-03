package dev.poncio.ClothAI.user;

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
@Table(schema = "clothai", name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE clothai.user SET active = false WHERE id=?")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;
    @Column(name = "active")
    private Boolean active;
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<CompanyEntity> companies;

}