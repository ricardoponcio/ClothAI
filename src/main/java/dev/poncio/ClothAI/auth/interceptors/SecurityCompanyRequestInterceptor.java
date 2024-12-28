package dev.poncio.ClothAI.auth.interceptors;

import dev.poncio.ClothAI.auth.AuthContext;
import dev.poncio.ClothAI.common.annotations.EnableRequestWithNoCompany;
import dev.poncio.ClothAI.company.CompanyEntity;
import dev.poncio.ClothAI.utils.SecurityAccessControl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SecurityCompanyRequestInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthContext authContext;

    @Autowired
    private SecurityAccessControl securityAccessControl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (this.authContext.getManagedCompany() != null) {
            CompanyEntity companyManaged = this.authContext.getManagedCompany();
            securityAccessControl.checkAccessForCompany(companyManaged);
        }
        return true;
    }

}
