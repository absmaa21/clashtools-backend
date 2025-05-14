package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.dto.RegisterRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.auth.AuthRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.auth.AuthResponseDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.RefreshToken;
import at.htlkaindorf.clashtoolsbackend.pojos.Role;
import at.htlkaindorf.clashtoolsbackend.pojos.User;
import at.htlkaindorf.clashtoolsbackend.repositories.RoleRepository;
import at.htlkaindorf.clashtoolsbackend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Service for handling authentication operations including user registration, login, and logout.
 * This service manages user credentials, token generation, and authentication state.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final RoleRepository roleRepository; // (Wird gebraucht für Standardrolle)

    /**
     * Registers a new user in the system.
     *
     * @param request The registration request containing username, email, and password
     * @throws IllegalArgumentException If the username is already taken or the email is already registered
     * @throws IllegalStateException If the default user role is not found in the database
     */
    @Transactional
    public void register(RegisterRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (userRepository.existsByMail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Default role ROLE_USER not found"));

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .mail(request.getEmail())
                .roles(Collections.singleton(userRole))
                .build();

        userRepository.save(user);
    }

    /**
     * Authenticates a user and generates JWT and refresh tokens.
     *
     * @param request The authentication request containing username and password
     * @return AuthResponseDTO containing JWT token and refresh token
     * @throws IllegalArgumentException If the user is not found or credentials are invalid
     */
    public AuthResponseDTO login(AuthRequestDTO request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String jwt = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResponseDTO(jwt, refreshToken.getToken());
    }

    /**
     * Logs out a user by deleting their refresh tokens.
     *
     * @param user The user to log out
     */
    public void logout(User user) {
        refreshTokenService.deleteByUser(user);
    }
}
