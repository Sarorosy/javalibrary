package com.saro.library.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Component
public class JwtUtil {
    private final String SECRET = "SARAVANAN BEST DEVELOPER - NEW BACKEND LIBRARY PROJECT";
    private final Long EXPIRATION = (long) 1000 * 60 * 60 * 24; //one day
    private final Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String email){
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith((SecretKey) secretKey)
                .compact();
    }

    public String extractEmail(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public  Boolean verifyToken(String token){
        try{
            extractEmail(token);
            return true;
        }catch(JwtException exception){
            return false;
        }
    }
}
