package dev.poncio.ClothAI.auth;

import dev.poncio.ClothAI.company.CompanyEntity;
import dev.poncio.ClothAI.token.dto.TokenDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class CustomTokenDetails implements CustomSessionDetails {

    private final TokenDTO token;

    @Setter
    private CompanyEntity company;

    public CustomTokenDetails(TokenDTO token) {
        this.token = token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        //for (Role role : roles) {
        authorities.add(new SimpleGrantedAuthority("M2M"));
        //}

        return authorities;
    }

}
