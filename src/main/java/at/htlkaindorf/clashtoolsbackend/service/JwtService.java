package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.exceptions.InvalidJwtException;
import at.htlkaindorf.clashtoolsbackend.exceptions.JwtExpiredException;
import at.htlkaindorf.clashtoolsbackend.pojos.Role;
import at.htlkaindorf.clashtoolsbackend.pojos.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

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
     * @throws JwtExpiredException If the token has expired
     * @throws InvalidJwtException If the token is invalid (malformed, unsupported, or has an invalid signature)
     */
    public String extractUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException ex) {
            logger.warn("JWT token expired: {}", ex.getMessage());
            throw new JwtExpiredException("JWT token has expired", ex);
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException ex) {
            logger.warn("Invalid JWT token: {}", ex.getMessage());
            throw new InvalidJwtException("Invalid JWT token: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            logger.error("Error processing JWT token: {}", ex.getMessage());
            throw new InvalidJwtException("Error processing JWT token", ex);
        }
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
        } catch (ExpiredJwtException ex) {
            logger.warn("JWT token expired: {}", ex.getMessage());
            return false;
        } catch (SignatureException ex) {
            logger.warn("Invalid JWT signature: {}", ex.getMessage());
            return false;
        } catch (MalformedJwtException ex) {
            logger.warn("Malformed JWT token: {}", ex.getMessage());
            return false;
        } catch (UnsupportedJwtException ex) {
            logger.warn("Unsupported JWT token: {}", ex.getMessage());
            return false;
        } catch (Exception ex) {
            logger.error("JWT token validation error: {}", ex.getMessage());
            return false;
        }
    }
}
