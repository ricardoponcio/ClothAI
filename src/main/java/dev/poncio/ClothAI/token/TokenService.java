package dev.poncio.ClothAI.token;

import dev.poncio.ClothAI.company.CompanyEntity;
import dev.poncio.ClothAI.token.dto.CreateTokenDTO;
import dev.poncio.ClothAI.token.dto.UpdateTokenDTO;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TokenService {
    @Autowired
    private ITokenRepository repository;
    @Autowired
    private TokenMapper mapper;
    @Autowired
    @Qualifier("partialUpdateMapper")
    private ModelMapper partialUpdateMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public TokenEntity findById(Long id) {
        return this.repository.findByIdAndActiveTrue(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<TokenEntity> listTokens(CompanyEntity company) throws Exception {
        return this.repository.findByCompanyAndActiveTrue(company);
    }

    public TokenEntity createNewToken(CompanyEntity company, CreateTokenDTO createTokenDTO) {
        TokenEntity newToken = this.mapper.fromDto(createTokenDTO);
        newToken.setActive(Boolean.TRUE);
        newToken.setCreatedAt(ZonedDateTime.now());
        newToken.setToken(UUID.randomUUID().toString());
        newToken.setCompany(company);
        return this.repository.save(newToken);
    }

    public TokenEntity updateToken(Long id, UpdateTokenDTO updateTokenDTO) {
        final var savedToken = this.findById(id);
        final var changedToken = mapper.fromDto(updateTokenDTO);
        partialUpdateMapper.map(changedToken, savedToken);
        return this.repository.save(savedToken);
    }

    public void deleteToken(Long id) {
        this.findById(id);
        this.repository.deleteById(id);
    }


}
