package dev.poncio.ClothAI.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICompanyRepository extends JpaRepository<CompanyEntity, Long> {

    List<CompanyEntity> findAllByActiveTrue();

}
