package com.example.ofunes.pennypanphone.Utiliidades;

public class AuthUtils {
    public static String getBearerToken(String token)
    {
        return "Bearer "+token;
    }
}
