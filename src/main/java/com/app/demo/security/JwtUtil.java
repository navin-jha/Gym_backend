package com.app.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET = "${JWT_SECRET}";

    private static final long EXPIRATION = 1000 * 60 * 60; // 1 hour

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // ================= GENERATE TOKEN =================
    public String generateToken(String email, String role) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    // ================= EXTRACT EMAIL =================
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ================= EXTRACT ROLE =================
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // ================= VALIDATE TOKEN =================
    public boolean validateToken(String token) {
        try {
            return !extractAllClaims(token)
                    .getExpiration()
                    .before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // ================= EXTRACT CLAIMS =================
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}