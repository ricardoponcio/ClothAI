package dev.poncio.ClothAI.auth.jwt.filter;

import dev.poncio.ClothAI.auth.jwt.utils.JwtUtils;
import dev.poncio.ClothAI.token.TokenEntity;
import dev.poncio.ClothAI.token.TokenMapper;
import dev.poncio.ClothAI.token.TokenService;
import dev.poncio.ClothAI.token.dto.TokenDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class M2MTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenMapper tokenMapper;

    private static final Logger logger = LoggerFactory.getLogger(AbstractJWTFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String tokenReq = getHeaderToken(request);
            if (tokenReq != null) {
                if (SecurityContextHolder.getContext().getAuthentication() != null) {
                    logger.warn("Already user authenticated, skipping M2M verification");
                } else {
                    TokenEntity token = this.tokenService.findToken(tokenReq);
                    TokenDTO tokenDTO = this.tokenMapper.toDto(token);
                    PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(
                            tokenDTO, null, Collections.singletonList(new SimpleGrantedAuthority("M2M"))
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set m2m authentication: {}", e.getLocalizedMessage());
        }

        filterChain.doFilter(request, response);
    }

    protected String getHeaderToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("ExternalToken");
        if (StringUtils.hasText(headerAuth)) {
            return headerAuth;
        }
        return null;
    }

}