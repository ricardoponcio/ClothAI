package dev.poncio.ClothAI.auth;

import dev.poncio.ClothAI.User.UserService;
import dev.poncio.ClothAI.User.dto.UserDTO;
import dev.poncio.ClothAI.auth.dto.RegisterRequestDTO;
import dev.poncio.ClothAI.auth.dto.RegisterResponseDTO;
import dev.poncio.ClothAI.auth.dto.SignUpRequestDTO;
import dev.poncio.ClothAI.auth.dto.SignUpResponseDTO;
import dev.poncio.ClothAI.auth.jwt.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${server.servlet.session.cookie.http-only}")
    private Boolean cookieHttpOnly;
    @Value("${server.servlet.session.cookie.secure}")
    private Boolean cookieSecure;
    @Value("${cookie.domain.base-url}")
    private String cookieDomainBaseUrl;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public RegisterResponseDTO registerUser(@Validated @RequestBody RegisterRequestDTO register, HttpServletResponse response) throws Exception {
        return this.authMapper.afterRegister(this.userService.createUser(register));
    }

    @PostMapping("/login")
    public SignUpResponseDTO authenticateUser(@Validated @RequestBody SignUpRequestDTO signUpRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        addAuthCookie(response, jwt);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return authMapper.map(userDetails.getUser());
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        SecurityContextHolder.getContext().setAuthentication(null);
        removeAuthCookie(response);
    }

    @GetMapping("/me")
    public UserDTO whoIs() {
        return userService.whoIsLogged();
    }

    private void addAuthCookie(HttpServletResponse response, String jwt) {
        Cookie cookie = new Cookie("CLOTH_AI_AUTH_ID", jwt);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setSecure(cookieSecure);
        cookie.setHttpOnly(cookieHttpOnly);
        cookie.setPath("/");
        cookie.setDomain(cookieDomainBaseUrl);
        response.addCookie(cookie);
    }

    private void removeAuthCookie(HttpServletResponse response) {
        addAuthCookie(response, null);
    }

}
