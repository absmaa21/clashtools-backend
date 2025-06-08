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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication Controller
 * Handles authentication operations like login, registration, refresh token, and logout.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    /**
     * Logs out the user.
     * Logs out the currently authenticated user and invalidates their session by removing any refresh tokens.
     *
     * @param userDetails The details of the currently authenticated user
     * @return ResponseEntity with a success message
     * @throws IllegalArgumentException if the user is not found
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        authService.logout(user);
        return ResponseEntity.ok(ApiResponse.success(null, "Logged out successfully"));
    }

    /**
     * Refresh JWT Token.
     * Refreshes the JWT token using a valid refresh token. If the refresh token is valid and not expired,
     * a new JWT token is generated while keeping the same refresh token.
     *
     * @param request The refresh token request containing the refresh token
     * @return ResponseEntity containing a new JWT token and the existing refresh token
     * @throws IllegalArgumentException if the refresh token is invalid or expired
     */
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> refreshToken(
            @RequestBody RefreshTokenRequestDTO request,
            HttpServletResponse httpServletResponse) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (refreshTokenService.isTokenExpired(refreshToken)) {
            refreshTokenRepository.delete(refreshToken);
            throw new IllegalArgumentException("Refresh token expired");
        }

        String newJwt = jwtService.generateToken(refreshToken.getUser());

        // Set access token as a cookie
        Cookie accessTokenCookie = new Cookie("access_token", newJwt);
        accessTokenCookie.setHttpOnly(false); // Prevent JavaScript access for better security
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(3600); // 1 hour
        accessTokenCookie.setSecure(false); // Disabled secure cookies as requested
        // SameSite=None typically requires Secure=true, so changing to Lax
        accessTokenCookie.setAttribute("SameSite", "Lax"); // For cross-origin requests
        httpServletResponse.addCookie(accessTokenCookie);

        AuthResponseDTO authResponse = new AuthResponseDTO(newJwt, request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success(authResponse, "Token refreshed successfully"));
    }

    /**
     * Register a new user.
     * Registers a new user with the provided registration details. The request is validated
     * to ensure all required fields are present and valid.
     *
     * @param request The registration request containing user details like username, email, and password
     * @return ResponseEntity with a success message
     * @throws jakarta.validation.ConstraintViolationException if validation fails
     * @throws at.htlkaindorf.clashtoolsbackend.exceptions.EmailAlreadyExistsException if the email is already in use
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(
            @Valid @RequestBody RegisterRequestDTO request) {
        authService.register(request);
        return ResponseEntity.ok(ApiResponse.success(null, "User registered successfully"));
    }

    /**
     * Login user.
     * Authenticates a user with the provided credentials and returns a JWT token along with a refresh token.
     * The request is validated to ensure all required fields are present.
     *
     * @param request The authentication request containing username and password
     * @return ResponseEntity containing JWT token and refresh token
     * @throws jakarta.validation.ConstraintViolationException if validation fails
     * @throws org.springframework.security.authentication.BadCredentialsException if credentials are invalid
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(
            @Valid @RequestBody AuthRequestDTO request,
            HttpServletResponse httpServletResponse) {
        AuthResponseDTO response = authService.login(request);

        // Set access token as a cookie
        Cookie accessTokenCookie = new Cookie("access_token", response.getAccessToken());
        accessTokenCookie.setHttpOnly(false); // Prevent JavaScript access for better security
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(3600); // 1 hour
        accessTokenCookie.setSecure(false); // Disabled secure cookies as requested
        // SameSite=None typically requires Secure=true, so changing to Lax
        accessTokenCookie.setAttribute("SameSite", "Lax"); // For cross-origin requests
        httpServletResponse.addCookie(accessTokenCookie);

        return ResponseEntity.ok(ApiResponse.success(response, "Login successful"));
    }
}
