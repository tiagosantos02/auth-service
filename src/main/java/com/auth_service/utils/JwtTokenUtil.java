package com.auth_service.utils;

import io.jsonwebtoken.*;
import java.util.Date;

public class JwtTokenUtil {
    // Em produção, use uma chave segura (nunca hardcode!) e armazene em variáveis de ambiente.
    private static final String SECRET_KEY = "Y2hhbmdlbWUtbXlzZWNyZXQtaGVyZS1zZWNyZXQtbG9s";
    private static final long EXPIRATION_TIME = 86400000; // 24h

    // Gera um token JWT
    public static String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // Identifica o usuário (normalmente email ou ID)
                .setIssuedAt(new Date()) // Data de criação
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiração
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Algoritmo de assinatura
                .compact();
    }

    // Valida um token JWT
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extrai o email do token
    public static String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}