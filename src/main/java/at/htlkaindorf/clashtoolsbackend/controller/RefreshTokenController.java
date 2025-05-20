package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.pojos.RefreshToken;
import at.htlkaindorf.clashtoolsbackend.pojos.User;
import at.htlkaindorf.clashtoolsbackend.repositories.RefreshTokenRepository;
import at.htlkaindorf.clashtoolsbackend.repositories.UserRepository;
import at.htlkaindorf.clashtoolsbackend.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Refresh Token Controller.
 * Provides administrative endpoints for managing refresh tokens in the system.
 * These endpoints are primarily for system administrators to monitor and manage
 * active refresh tokens.
 */
@RestController
@RequestMapping("/api/refresh-tokens")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class RefreshTokenController {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    /**
     * Get all refresh tokens.
     * Retrieves a list of all refresh tokens in the system.
     * This endpoint is restricted to administrators.
     *
     * @return ResponseEntity containing a list of RefreshToken objects
     */
    @GetMapping
    public ResponseEntity<List<RefreshToken>> getAllRefreshTokens() {
        List<RefreshToken> tokens = refreshTokenRepository.findAll();
        return ResponseEntity.ok(tokens);
    }

    /**
     * Get refresh tokens for a specific user.
     * Retrieves all refresh tokens associated with a specific user.
     * This endpoint is restricted to administrators.
     *
     * @param userId The ID of the user whose tokens to retrieve
     * @return ResponseEntity containing a list of RefreshToken objects
     * @throws IllegalArgumentException if the user is not found
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RefreshToken>> getRefreshTokensByUser(
            @PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<RefreshToken> tokens = refreshTokenRepository.findByUser(user);
        return ResponseEntity.ok(tokens);
    }

    /**
     * Invalidate a specific refresh token.
     * Deletes a refresh token identified by its ID.
     * This endpoint is restricted to administrators.
     *
     * @param tokenId The ID of the token to invalidate
     * @return ResponseEntity with no content, indicating successful deletion
     * @throws IllegalArgumentException if the token is not found
     */
    @DeleteMapping("/{tokenId}")
    public ResponseEntity<Void> invalidateToken(
            @PathVariable Long tokenId) {
        RefreshToken token = refreshTokenRepository.findById(tokenId)
                .orElseThrow(() -> new IllegalArgumentException("Token not found"));

        refreshTokenRepository.delete(token);
        return ResponseEntity.noContent().build();
    }

    /**
     * Invalidate all refresh tokens for a specific user.
     * Deletes all refresh tokens associated with a specific user.
     * This endpoint is restricted to administrators.
     *
     * @param userId The ID of the user whose tokens to invalidate
     * @return ResponseEntity with no content, indicating successful deletion
     * @throws IllegalArgumentException if the user is not found
     */
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> invalidateAllTokensForUser(
            @PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        refreshTokenService.deleteByUser(user);
        return ResponseEntity.noContent().build();
    }
}
