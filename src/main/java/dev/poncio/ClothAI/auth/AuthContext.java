package dev.poncio.ClothAI.auth;

import dev.poncio.ClothAI.company.CompanyEntity;
import dev.poncio.ClothAI.token.TokenEntity;
import dev.poncio.ClothAI.token.TokenService;
import dev.poncio.ClothAI.token.dto.TokenDTO;
import dev.poncio.ClothAI.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthContext {

    @Autowired
    private TokenService tokenService;

    private CustomSessionDetails getSessionData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AbstractAuthenticationToken authenticationToken)) return null;
        Object principal = authenticationToken.getPrincipal();
        if (!(principal instanceof CustomSessionDetails sessionDetails)) return null;
        return sessionDetails;
    }

    public boolean isM2MRequest() {
        CustomSessionDetails sessionDetails = getSessionData();
        return sessionDetails instanceof CustomTokenDetails;
    }

    public TokenDTO getTokenFromM2MRequest() {
        CustomSessionDetails sessionDetails = getSessionData();
        assert sessionDetails != null;
        return ((CustomTokenDetails) sessionDetails).getToken();
    }

    public TokenEntity getTokenEntityFromM2MRequest() {
        return tokenService.findToken(getTokenFromM2MRequest().getToken());
    }

    public UserEntity getLoggedUser() {
        CustomSessionDetails sessionDetails = getSessionData();
        assert sessionDetails != null;
        return ((CustomUserDetails) sessionDetails).getUser();
    }

    public Long getLoggedUserId() {
        UserEntity loggedUser = getLoggedUser();
        return loggedUser.getId();
    }

    public CompanyEntity getManagedCompany() {
        CustomSessionDetails sessionDetails = getSessionData();
        assert sessionDetails != null;
        return sessionDetails.getCompany();
    }

}
