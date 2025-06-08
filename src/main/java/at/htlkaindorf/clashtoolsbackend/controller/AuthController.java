package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.ApiResponse;
import at.htlkaindorf.clashtoolsbackend.dto.auth.AuthRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.auth.AuthResponseDTO;
import at.htlkaindorf.clashtoolsbackend.dto.RefreshTokenRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.RegisterRequestDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.RefreshToken;
import at.htlkaindorf.clashtoolsbackend.pojos.User;
import at.htlkaindorf.clashtoolsbackend.repositories.RefreshTokenRepository;
import at.htlkaindorf.clashtoolsbackend.repositories.UserRepository;
import at.htlkaindorf.clashtoolsbackend.service.AuthService;
import at.htlkaindorf.clashtoolsbackend.service.JwtService;
import at.htlkaindorf.clashtoolsbackend.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API for user authentication operations")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    private void createAccessTokenCookie(String token, HttpServletResponse response) {
        Cookie accessTokenCookie = new Cookie("access_token", token);
        accessTokenCookie.setHttpOnly(false);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(3600);
        accessTokenCookie.setSecure(false);
        accessTokenCookie.setAttribute("SameSite", "Lax");
        response.addCookie(accessTokenCookie);
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout user",
               description = "Logs out the currently authenticated user and invalidates their session")
    public ResponseEntity<ApiResponse<Void>> logout(
            @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("Logging out user: {}", userDetails.getUsername());
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        authService.logout(user);
        return ResponseEntity.ok(ApiResponse.success(null, "Logged out successfully"));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh JWT token",
               description = "Refreshes the JWT token using a valid refresh token")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> refreshToken(
            @RequestBody RefreshTokenRequestDTO request,
            HttpServletResponse httpServletResponse) {
        log.debug("Refreshing token for refresh token: {}", request.getRefreshToken());
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (refreshTokenService.isTokenExpired(refreshToken)) {
            log.warn("Refresh token expired: {}", request.getRefreshToken());
            refreshTokenRepository.delete(refreshToken);
            throw new IllegalArgumentException("Refresh token expired");
        }

        String newJwt = jwtService.generateToken(refreshToken.getUser());
        createAccessTokenCookie(newJwt, httpServletResponse);

        AuthResponseDTO authResponse = new AuthResponseDTO(newJwt, request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success(authResponse, "Token refreshed successfully"));
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user",
               description = "Creates a new user account with the provided details")
    public ResponseEntity<ApiResponse<Void>> register(
            @Valid @RequestBody RegisterRequestDTO request) {
        log.debug("Registering new user with username: {}", request.getUsername());
        authService.register(request);
        return ResponseEntity.ok(ApiResponse.success(null, "User registered successfully"));
    }

    @PostMapping("/login")
    @Operation(summary = "Login user",
               description = "Authenticates a user and returns access and refresh tokens")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(
            @Valid @RequestBody AuthRequestDTO request,
            HttpServletResponse httpServletResponse) {
        log.debug("Login attempt for user: {}", request.getUsername());
        AuthResponseDTO response = authService.login(request);

        createAccessTokenCookie(response.getAccessToken(), httpServletResponse);

        return ResponseEntity.ok(ApiResponse.success(response, "Login successful"));
    }
}
