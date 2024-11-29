package dev.poncio.ClothAI.User;

import dev.poncio.ClothAI.auth.dto.RegisterRequestDTO;
import dev.poncio.ClothAI.User.dto.UserDTO;
import dev.poncio.ClothAI.auth.AuthContext;
import dev.poncio.ClothAI.auth.CustomUserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private IUserRepository repository;
    @Autowired
    private UserMapper mapper;
    @Autowired
    @Qualifier("partialUpdateMapper")
    private ModelMapper partialUpdateMapper;
    @Autowired
    private AuthContext authContext;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private UserEntity findUserByEmail(String email) {
        return this.repository.findByEmailAndActiveTrue(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDTO whoIsLogged() {
        return this.mapper.toDto(this.authContext.getLoggedUser());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails(this.findUserByEmail(username));
    }

    public UserEntity createUser(RegisterRequestDTO registerRequestDTO) throws Exception {
        try {
            findUserByEmail(registerRequestDTO.getEmail());
            throw new Exception("Email already registered");
        } catch (Exception e) {
            // User no found
        }
        UserEntity newUser = this.mapper.fromDto(registerRequestDTO);
        newUser.setActive(Boolean.TRUE);
        newUser.setCreatedAt(ZonedDateTime.now());
        newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
        return this.repository.save(newUser);
    }

}
