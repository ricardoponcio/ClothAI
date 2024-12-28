package dev.poncio.ClothAI.auth.interceptors;

import dev.poncio.ClothAI.auth.AuthContext;
import dev.poncio.ClothAI.common.annotations.EnableRequestWithNoCompany;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class EnableRequestWithNoCompanyInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthContext authContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        EnableRequestWithNoCompany[] annotation = handlerMethod.getBean().getClass().getAnnotationsByType(EnableRequestWithNoCompany.class);
        boolean hasAnnotation = annotation.length > 0;

        if (this.authContext.getManagedCompany() == null && !hasAnnotation) {
            String source = this.authContext.isM2MRequest() ? "header" : "cookie";
            throw new RuntimeException(String.format("Company %s is required for this request", source));
        }

        return true;
    }

}
