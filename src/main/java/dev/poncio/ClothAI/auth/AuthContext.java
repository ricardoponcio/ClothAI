package dev.poncio.ClothAI.auth;

import dev.poncio.ClothAI.User.UserEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthContext {

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
