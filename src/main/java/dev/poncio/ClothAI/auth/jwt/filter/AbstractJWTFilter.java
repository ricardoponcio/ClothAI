package dev.poncio.ClothAI.auth.jwt.filter;

import dev.poncio.ClothAI.auth.CustomUserDetails;
import dev.poncio.ClothAI.auth.jwt.utils.JwtUtils;
import dev.poncio.ClothAI.company.CompanyEntity;
import dev.poncio.ClothAI.user.UserService;
import dev.poncio.ClothAI.utils.CookieManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public abstract class AbstractJWTFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AbstractJWTFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(CookieManager.USER_COOKIE_KEY, request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getPayloadFromJwtToken(jwt);
                UserDetails userDetails = this.userService.loadUserByUsername(username);

                String jwtCompany = parseJwt(CookieManager.COMPANY_COOKIE_KEY, request);
                if (jwtCompany != null && jwtUtils.validateJwtToken(jwtCompany)) {
                    String companyId = jwtUtils.getPayloadFromJwtToken(jwtCompany);
                    ((CustomUserDetails) userDetails).setCompany(CompanyEntity.builder().id(Long.parseLong(companyId)).build());
                }

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getLocalizedMessage());
        }

        filterChain.doFilter(request, response);
    }

    protected abstract String parseJwt(String keyName, HttpServletRequest request);

}