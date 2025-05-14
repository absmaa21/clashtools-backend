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

/**
 * Service for handling JWT (JSON Web Token) operations.
 * This service manages token generation, validation, and extraction of user information from tokens.
 */
@Service
public class JwtService {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Automatisch erzeugt
    private static final Duration EXPIRATION_TIME = Duration.ofHours(1); // 1h Token-Lebensdauer

    /**
     * Generates a JWT token for the specified user.
     * The token includes the username and user roles as claims and has an expiration time.
     *
     * @param user The user for whom to generate the token
     * @return A JWT token string
     */
    public String generateToken(User user) {
        Date now = new Date();

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


    /**
     * Extracts the username from a JWT token.
     *
     * @param token The JWT token string
     * @return The username extracted from the token
     * @throws io.jsonwebtoken.JwtException If the token is invalid or expired
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Validates a JWT token.
     * Checks if the token is properly signed and not expired.
     *
     * @param token The JWT token string to validate
     * @return true if the token is valid, false otherwise
     */
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
