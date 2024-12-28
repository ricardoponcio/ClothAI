package dev.poncio.ClothAI.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieManager {

    @Value("${server.servlet.session.cookie.http-only}")
    private Boolean cookieHttpOnly;
    @Value("${server.servlet.session.cookie.secure}")
    private Boolean cookieSecure;
    @Value("${cookie.domain.base-url}")
    private String cookieDomainBaseUrl;

    public static final String USER_COOKIE_KEY = "CLOTH_AI_AUTH_ID";
    public static final String COMPANY_COOKIE_KEY = "CLOTH_AI_CPNY_ID";

    public void addUserCookie(HttpServletResponse response, String jwt) {
        addCookie(USER_COOKIE_KEY, response, jwt);
    }

    public void addCompanyCookie(HttpServletResponse response, String jwt) {
        addCookie(COMPANY_COOKIE_KEY, response, jwt);
    }

    private void addCookie(String cookieKey, HttpServletResponse response, String jwt) {
        Cookie cookie = new Cookie(cookieKey, jwt);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setSecure(cookieSecure);
        cookie.setHttpOnly(cookieHttpOnly);
        cookie.setPath("/");
        cookie.setDomain(cookieDomainBaseUrl);
        response.addCookie(cookie);
    }

    public void removeUserCookie(HttpServletResponse response) {
        removeCookie(USER_COOKIE_KEY, response);
    }

    public void removeCompanyCookie(HttpServletResponse response) {
        removeCookie(COMPANY_COOKIE_KEY, response);
    }

    private void removeCookie(String cookieKey, HttpServletResponse response) {
        addCookie(cookieKey, response, null);
    }

}
