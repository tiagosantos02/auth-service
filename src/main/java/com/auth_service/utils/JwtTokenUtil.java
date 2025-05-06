package com.auth_service.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    // Gera um token JWT
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // Identifica o usuário (normalmente email ou ID)
                .setIssuedAt(new Date()) // Data de criação
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // Expiração
                .signWith(SignatureAlgorithm.HS256, secretKey) // Algoritmo de assinatura
                .compact();
    }

    // Valida um token JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extrai o email do token
    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
