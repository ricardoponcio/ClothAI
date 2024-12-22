package dev.poncio.ClothAI.auth;

import dev.poncio.ClothAI.token.TokenEntity;
import dev.poncio.ClothAI.token.TokenService;
import dev.poncio.ClothAI.token.dto.TokenDTO;
import dev.poncio.ClothAI.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthContext {

    @Autowired
    private TokenService tokenService;

    public boolean isM2MRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof UsernamePasswordAuthenticationToken);
    }

    public TokenDTO getTokenFromM2MRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof PreAuthenticatedAuthenticationToken authenticatedToken)) return null;
        Object userPrincipal = authenticatedToken.getPrincipal();
        if (!(userPrincipal instanceof TokenDTO token)) return null;
        return token;
    }

    public TokenEntity getTokenEntityFromM2MRequest() {
        return tokenService.findToken(getTokenFromM2MRequest().getToken());
    }

    public UserEntity getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof UsernamePasswordAuthenticationToken authenticatedUserToken)) return null;
        Object userPrincipal = authenticatedUserToken.getPrincipal();
        if (!(userPrincipal instanceof CustomUserDetails userDetails)) return null;
        if (userDetails == null) return null;
        return userDetails.getUser();
    }

    public Long getLoggedUserId() {
        UserEntity loggedUser = getLoggedUser();
        if (loggedUser == null) return null;
        return loggedUser.getId();
    }

}
