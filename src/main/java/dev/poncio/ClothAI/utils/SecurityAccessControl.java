package dev.poncio.ClothAI.utils;

import dev.poncio.ClothAI.auth.AuthContext;
import dev.poncio.ClothAI.company.CompanyEntity;
import dev.poncio.ClothAI.token.TokenEntity;
import dev.poncio.ClothAI.token.TokenService;
import dev.poncio.ClothAI.token.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityAccessControl {

    @Autowired
    private AuthContext authContext;

    @Autowired
    private TokenService tokenService;

    public void checkAccessForCompany(Long companyId) {
        checkAccessForCompany(CompanyEntity.builder().id(companyId).build());
    }

    public void checkAccessForCompany(CompanyEntity company) {
        if (this.authContext.isM2MRequest()) {
            TokenDTO tokenDTO = this.authContext.getTokenFromM2MRequest();
            TokenEntity token = this.tokenService.findToken(tokenDTO.getToken());
            if (!token.getCompany().getId().equals(company.getId())) {
                throw new SecurityException();
            }
        } else {
            if (this.authContext.getLoggedUser().getCompanies().stream().noneMatch(c -> c.getId().equals(company.getId()))) {
                throw new SecurityException();
            }
        }
    }

}
