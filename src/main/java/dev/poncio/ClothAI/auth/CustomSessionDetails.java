package dev.poncio.ClothAI.auth;

import dev.poncio.ClothAI.company.CompanyEntity;
import dev.poncio.ClothAI.token.dto.TokenDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface CustomSessionDetails {

    public CompanyEntity getCompany();

    public void setCompany(CompanyEntity company);

    public Collection<? extends GrantedAuthority> getAuthorities();

}
