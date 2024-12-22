package dev.poncio.ClothAI.clothresource;

import dev.poncio.ClothAI.company.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IClothResourceRepository extends JpaRepository<ClothResourceEntity, Long> {

    List<ClothResourceEntity> findAllByCompanyAndActiveTrue(CompanyEntity company);

    Optional<ClothResourceEntity> findByIdAndActiveTrue(Long id);

    Optional<ClothResourceEntity> findByIdentificationAndActiveTrue(String identification);

}
