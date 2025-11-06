package ru.itmo.infsec.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtManager {

    private final SecretKey key;
    private final long ttlMs;

    public JwtManager(String secret, long ttlMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.ttlMs = ttlMs;
    }

    public String generateToken(String login) {
        var now = new Date();
        return Jwts.builder()
                .subject(login)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + ttlMs))
                .signWith(key)
                .compact();
    }

    public String getUsername(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return claims.getPayload().getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }
}
