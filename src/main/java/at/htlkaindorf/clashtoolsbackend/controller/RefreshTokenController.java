package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.ApiResponse;
import at.htlkaindorf.clashtoolsbackend.pojos.RefreshToken;
import at.htlkaindorf.clashtoolsbackend.pojos.User;
import at.htlkaindorf.clashtoolsbackend.repositories.RefreshTokenRepository;
import at.htlkaindorf.clashtoolsbackend.repositories.UserRepository;
import at.htlkaindorf.clashtoolsbackend.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/refresh-tokens")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Refresh Tokens", description = "Administrative API for managing refresh tokens")
public class RefreshTokenController {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    @GetMapping
    @Operation(summary = "Get all refresh tokens",
               description = "Retrieves a list of all refresh tokens in the system")
    public ResponseEntity<ApiResponse<List<RefreshToken>>> getAllRefreshTokens() {
        log.debug("Fetching all refresh tokens");
        List<RefreshToken> tokens = refreshTokenRepository.findAll();
        return ResponseEntity.ok(ApiResponse.success(tokens));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get refresh tokens for a specific user",
               description = "Retrieves all refresh tokens associated with a specific user")
    public ResponseEntity<ApiResponse<List<RefreshToken>>> getRefreshTokensByUser(
            @PathVariable Long userId) {
        log.debug("Fetching refresh tokens for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<RefreshToken> tokens = refreshTokenRepository.findByUser(user);
        return ResponseEntity.ok(ApiResponse.success(tokens));
    }

    @DeleteMapping("/{tokenId}")
    @Operation(summary = "Invalidate a specific refresh token",
               description = "Deletes a refresh token identified by its ID")
    public ResponseEntity<ApiResponse<Void>> invalidateToken(
            @PathVariable Long tokenId) {
        log.debug("Invalidating refresh token with ID: {}", tokenId);
        RefreshToken token = refreshTokenRepository.findById(tokenId)
                .orElseThrow(() -> new IllegalArgumentException("Token not found"));

        refreshTokenRepository.delete(token);
        return ResponseEntity.ok(ApiResponse.success(null, "Token invalidated successfully"));
    }

    @DeleteMapping("/user/{userId}")
    @Operation(summary = "Invalidate all refresh tokens for a specific user",
               description = "Deletes all refresh tokens associated with a specific user")
    public ResponseEntity<ApiResponse<Void>> invalidateAllTokensForUser(
            @PathVariable Long userId) {
        log.debug("Invalidating all refresh tokens for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        refreshTokenService.deleteByUser(user);
        return ResponseEntity.ok(ApiResponse.success(null, "All tokens for user invalidated successfully"));
    }
}
