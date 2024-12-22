package dev.poncio.ClothAI.auth.interceptors;

import dev.poncio.ClothAI.auth.AuthContext;
import dev.poncio.ClothAI.common.annotations.OnlyM2MEndpoint;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class OnlyM2MEndpointsInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthContext authContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        OnlyM2MEndpoint[] annotation = handlerMethod.getBean().getClass().getAnnotationsByType(OnlyM2MEndpoint.class);
        boolean hasAnnotation = annotation.length > 0;

        if (hasAnnotation && !this.authContext.isM2MRequest()) {
            throw new RuntimeException("Controller only allowed to M2M Requests");
        }
        if (!hasAnnotation && this.authContext.isM2MRequest()) {
            throw new RuntimeException("Controller is not allowed to M2M Requests");
        }

        return true;
    }

}
