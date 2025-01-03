package dev.poncio.ClothAI.company;

import dev.poncio.ClothAI.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(schema = "clothai", name = "company")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE clothai.company SET active = false WHERE id=?")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;
    @Column(name = "active")
    private Boolean active;
    @ManyToMany
    @JoinTable(schema = "clothai", name = "company_user",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserEntity> users;

}