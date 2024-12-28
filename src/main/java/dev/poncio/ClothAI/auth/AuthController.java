package dev.poncio.ClothAI.auth;

import dev.poncio.ClothAI.common.annotations.EnableRequestWithNoCompany;
import dev.poncio.ClothAI.user.UserService;
import dev.poncio.ClothAI.user.dto.UserDTO;
import dev.poncio.ClothAI.auth.dto.RegisterRequestDTO;
import dev.poncio.ClothAI.auth.dto.RegisterResponseDTO;
import dev.poncio.ClothAI.auth.dto.SignUpRequestDTO;
import dev.poncio.ClothAI.auth.dto.SignUpResponseDTO;
import dev.poncio.ClothAI.auth.jwt.utils.JwtUtils;
import dev.poncio.ClothAI.utils.CookieManager;
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
@RequestMapping("/api/auth")
@EnableRequestWithNoCompany
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CookieManager cookieManager;

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

        this.cookieManager.addUserCookie(response, jwt);
        this.cookieManager.removeCompanyCookie(response);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return authMapper.map(userDetails.getUser());
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        SecurityContextHolder.getContext().setAuthentication(null);
        this.cookieManager.removeCompanyCookie(response);
        this.cookieManager.removeUserCookie(response);
    }

    @GetMapping("/me")
    public UserDTO whoIs() {
        return userService.whoIsLogged();
    }

}
