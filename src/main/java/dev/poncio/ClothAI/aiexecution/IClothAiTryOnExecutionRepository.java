package dev.poncio.ClothAI.aiexecution;

import dev.poncio.ClothAI.company.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IClothAiTryOnExecutionRepository extends JpaRepository<ClothAiTryOnExecutionEntity, Long> {

    List<ClothAiTryOnExecutionEntity> findAllByStartedAtIsNull();

    List<ClothAiTryOnExecutionEntity> findAllByCompanyAndStartedAtIsNull(CompanyEntity company);

    Optional<ClothAiTryOnExecutionEntity> findByExecutionIdentification(String executionIdentification);

}
