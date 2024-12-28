package dev.poncio.ClothAI.token;

import dev.poncio.ClothAI.common.interfaces.IEntityWithCompany;
import dev.poncio.ClothAI.common.services.CommonService;
import dev.poncio.ClothAI.company.CompanyEntity;
import dev.poncio.ClothAI.token.dto.CreateTokenDTO;
import dev.poncio.ClothAI.token.dto.UpdateTokenDTO;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TokenService extends CommonService<TokenEntity> {
    @Autowired
    private ITokenRepository repository;
    @Autowired
    private TokenMapper mapper;
    @Autowired
    @Qualifier("partialUpdateMapper")
    private ModelMapper partialUpdateMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    protected TokenEntity findById(Long id) {
        return this.repository.findByIdAndActiveTrue(id).orElseThrow(EntityNotFoundException::new);
    }

    public TokenEntity findToken(String tokenId) {
        TokenEntity token = this.repository.findByTokenAndActiveTrue(tokenId).orElseThrow(EntityNotFoundException::new);
        super.checkSecurityEntity(token);
        return token;
    }

    public List<TokenEntity> listTokens() throws Exception {
        return this.repository.findByCompanyAndActiveTrue(super.getAuthContext().getManagedCompany());
    }

    public TokenEntity createNewToken(CreateTokenDTO createTokenDTO) {
        TokenEntity newToken = this.mapper.fromDto(createTokenDTO);
        newToken.setActive(Boolean.TRUE);
        newToken.setCreatedAt(ZonedDateTime.now());
        newToken.setToken(UUID.randomUUID().toString());
        newToken.setCompany(super.getAuthContext().getManagedCompany());
        return this.repository.save(newToken);
    }

    public TokenEntity updateToken(Long id, UpdateTokenDTO updateTokenDTO) {
        final var savedToken = this.findByIdSecure(id);
        final var changedToken = mapper.fromDto(updateTokenDTO);
        partialUpdateMapper.map(changedToken, savedToken);
        return this.repository.save(savedToken);
    }

    public void deleteToken(Long id) {
        this.findByIdSecure(id);
        this.repository.deleteById(id);
    }

}
