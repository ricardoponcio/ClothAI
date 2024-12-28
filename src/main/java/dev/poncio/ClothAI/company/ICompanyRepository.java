package dev.poncio.ClothAI.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICompanyRepository extends JpaRepository<CompanyEntity, Long> {

    @Query("SELECT c FROM CompanyEntity c JOIN c.users u WHERE u.id = :userId AND c.active is TRUE")
    List<CompanyEntity> findByUserId(@Param("userId") Long userId);

    Optional<CompanyEntity> findByIdAndActiveTrue(Long id);

}
