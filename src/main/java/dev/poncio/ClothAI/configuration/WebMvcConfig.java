package dev.poncio.ClothAI.configuration;

import dev.poncio.ClothAI.auth.AuthConstants;
import dev.poncio.ClothAI.auth.interceptors.OnlyM2MEndpointsInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:8081")
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }

    @Bean
    public OnlyM2MEndpointsInterceptor onlyM2MEndpointInterceptor() {
        return new OnlyM2MEndpointsInterceptor();
    }

    public @Override void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(onlyM2MEndpointInterceptor()).excludePathPatterns(AuthConstants.getExcludeAuthFilterPaths());
    }


}