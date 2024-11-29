package dev.poncio.ClothAI.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICompanyRepository extends JpaRepository<CompanyEntity, Long> {

    List<CompanyEntity> findAllByActiveTrue();

    Optional<CompanyEntity> findByIdAndActiveTrue(Long id);

}
