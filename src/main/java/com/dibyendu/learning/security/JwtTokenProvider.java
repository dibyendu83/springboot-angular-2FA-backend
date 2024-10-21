package com.dibyendu.learning.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret.key}")
    private String secretKey;
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    public String generateToken(Map<String, Object> claims, String subject) {
        long currentTimeMillis = System.currentTimeMillis();
        Date expiryDate = new Date(currentTimeMillis + EXPIRATION_TIME);

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey);

        return builder.compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractClaims(token).getSubject();
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
