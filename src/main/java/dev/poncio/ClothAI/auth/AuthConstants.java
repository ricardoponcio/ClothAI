package dev.poncio.ClothAI.auth;

public class AuthConstants {

    public static String[] getExcludeAuthFilterPaths() {
        return new String[]{"/api/auth/login", "/api/auth/register"};
    }

}
