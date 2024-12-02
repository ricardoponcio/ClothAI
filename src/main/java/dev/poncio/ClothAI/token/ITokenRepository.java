package dev.poncio.ClothAI.token;

import dev.poncio.ClothAI.company.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITokenRepository extends JpaRepository<TokenEntity, Long> {

    List<TokenEntity> findByCompanyAndActiveTrue(CompanyEntity company);

    Optional<TokenEntity> findByCompanyAndTokenAndActiveTrue(CompanyEntity company, String token);

    Optional<TokenEntity> findByIdAndActiveTrue(Long id);

}
