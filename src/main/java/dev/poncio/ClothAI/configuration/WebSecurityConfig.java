package dev.poncio.ClothAI.configuration;

import dev.poncio.ClothAI.auth.AuthConstants;
import dev.poncio.ClothAI.auth.jwt.filter.AbstractJWTFilter;
import dev.poncio.ClothAI.auth.jwt.filter.JWTCookieFilter;
import dev.poncio.ClothAI.auth.jwt.filter.M2MTokenFilter;
import dev.poncio.ClothAI.auth.jwt.utils.AuthEntryPointJwt;
import dev.poncio.ClothAI.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AbstractJWTFilter authenticationJwtTokenFilter() {
        return new JWTCookieFilter();
    }

    @Bean
    public M2MTokenFilter apiJwtTokenFilter() {
        return new M2MTokenFilter();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AuthConstants.getExcludeAuthFilterPaths()).permitAll()
                        //.requestMatchers("/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                );
        http.addFilterAfter(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(apiJwtTokenFilter(), JWTCookieFilter.class);
        http.authenticationProvider(authenticationProvider());
        return http.build();
    }

}
