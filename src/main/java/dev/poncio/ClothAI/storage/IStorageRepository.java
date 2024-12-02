package dev.poncio.ClothAI.storage;

import dev.poncio.ClothAI.company.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IStorageRepository extends JpaRepository<StorageEntity, Long> {

    Optional<StorageEntity> findOneByCompanyAndActiveTrue(CompanyEntity company);

    Optional<StorageEntity> findByIdAndActiveTrue(Long id);

}
