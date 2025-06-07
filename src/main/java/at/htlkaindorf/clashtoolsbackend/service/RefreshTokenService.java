package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.pojos.RefreshToken;
import at.htlkaindorf.clashtoolsbackend.pojos.User;
import at.htlkaindorf.clashtoolsbackend.repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * Service for managing refresh tokens used in the authentication process.
 * Refresh tokens allow users to get new JWT tokens without re-authentication.
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private static final Duration REFRESH_TOKEN_EXPIRATION = Duration.ofDays(7);

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Creates a new refresh token for the specified user.
     * If the user already has a refresh token, it will be deleted first.
     *
     * @param user The user for whom to create a refresh token
     * @return The newly created refresh token
     */
    public RefreshToken createRefreshToken(User user) {
        refreshTokenRepository.deleteByUser(user);
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plus(REFRESH_TOKEN_EXPIRATION))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Checks if a refresh token is expired.
     *
     * @param token The refresh token to check
     * @return true if the token is expired, false otherwise
     */
    public boolean isTokenExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(Instant.now());
    }

    /**
     * Deletes all refresh tokens associated with a user.
     * This is typically called during logout or when issuing a new refresh token.
     *
     * @param user The user whose refresh tokens should be deleted
     */
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }
}
