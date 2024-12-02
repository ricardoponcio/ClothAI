package dev.poncio.ClothAI.utils;

import dev.poncio.ClothAI.auth.AuthContext;
import dev.poncio.ClothAI.company.CompanyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityAccessControl {

    @Autowired
    private AuthContext authContext;

    public void checkAccessForCompany(Long companyId) {
        checkAccessForCompany(CompanyEntity.builder().id(companyId).build());
    }

    public void checkAccessForCompany(CompanyEntity company) {
        if (authContext.getLoggedUser().getCompanies().stream().noneMatch(c -> c.getId().equals(company.getId()))) {
            throw new SecurityException();
        }
    }

}
