package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.pojos.Role;
import at.htlkaindorf.clashtoolsbackend.pojos.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Automatisch erzeugt
    //private static final long EXPIRATION_TIME_MS = 86400000; // 24h Token-Lebensdauer
    private static final Duration EXPIRATION_TIME = Duration.ofHours(1); // 1h Token-Lebensdauer


    // JWT generieren
    public String generateToken(User user) {
        Date now = new Date();
        //Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME_MS);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", user.getRoles().stream()
                        .map(Role::getName) // Role-Name extrahieren
                        .toList()
                )
                .setIssuedAt(now)
                .setExpiration(Date.from(Instant.now().plus(EXPIRATION_TIME)))
                .signWith(secretKey)
                .compact();
    }


    // Username aus JWT extrahieren
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Token verifizieren
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
