package br.com.fiap3esa.spring_boot_project.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expirationMs;

    public JwtUtil(@Value("${app.jwt.secret}") String secret,
                   @Value("${app.jwt.expiration-ms}") long expirationMs) {

        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }

    public String generateToken(String username, Set<String> roles) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            JwtParser parser = Jwts.parser()
                    .verifyWith(secretKey)
                    .build();

            Jws<Claims> claims = parser.parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public String getUsername(String token) {

        JwtParser parser = Jwts.parser()
                .verifyWith(secretKey)
                .build();

        Claims body = parser.parseClaimsJws(token).getBody();
        return body.getSubject();
    }

    @SuppressWarnings("unchecked")
    public Set<String> getRoles(String token) {

        JwtParser parser = Jwts.parser()
                .verifyWith(secretKey)
                .build();

        Claims body = parser.parseClaimsJws(token).getBody();

        Object roles = body.get("roles");
        if (roles instanceof Collection) {
            return ((Collection<?>) roles).stream().map(Object::toString).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    public long getExpirationMs() {
        return expirationMs;
    }
}