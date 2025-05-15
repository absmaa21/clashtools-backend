package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.constants.RoleConstants;
import at.htlkaindorf.clashtoolsbackend.dto.RegisterRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.auth.AuthRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.auth.AuthResponseDTO;
import at.htlkaindorf.clashtoolsbackend.exceptions.EmailAlreadyExistsException;
import at.htlkaindorf.clashtoolsbackend.exceptions.InvalidCredentialsException;
import at.htlkaindorf.clashtoolsbackend.exceptions.RoleNotFoundException;
import at.htlkaindorf.clashtoolsbackend.exceptions.UserNotFoundException;
import at.htlkaindorf.clashtoolsbackend.exceptions.UsernameAlreadyExistsException;
import at.htlkaindorf.clashtoolsbackend.pojos.RefreshToken;
import at.htlkaindorf.clashtoolsbackend.pojos.Role;
import at.htlkaindorf.clashtoolsbackend.pojos.User;
import at.htlkaindorf.clashtoolsbackend.repositories.RoleRepository;
import at.htlkaindorf.clashtoolsbackend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final RoleRepository roleRepository; // Used for default role assignment

    /**
     * Registers a new user in the system.
     *
     * @param request The registration request containing username, email, and password
     * @throws IllegalArgumentException If the username is already taken or the email is already registered
     * @throws IllegalStateException If the default user role is not found in the database
     */
    @Transactional
    public void register(RegisterRequestDTO request) {
        if (request == null) {
            logger.error("Registration failed: Request object is null");
            throw new IllegalArgumentException("Registration request cannot be null");
        }

        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            logger.warn("Registration failed: Username is null or empty");
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            logger.warn("Registration failed: Email is null or empty");
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            logger.warn("Registration failed: Password is null or empty");
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        logger.debug("Attempting to register new user with username: {}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            logger.warn("Registration failed: Username '{}' already taken", request.getUsername());
            throw new UsernameAlreadyExistsException("Username already taken");
        }
        if (userRepository.existsByMail(request.getEmail())) {
            logger.warn("Registration failed: Email '{}' already registered", request.getEmail());
            throw new EmailAlreadyExistsException("Email already registered");
        }

        Role userRole = roleRepository.findByName(RoleConstants.ROLE_USER.getRoleName())
                .orElseThrow(() -> {
                    logger.error("Default role {} not found in database", RoleConstants.ROLE_USER.getRoleName());
                    return new RoleNotFoundException("Default role " + RoleConstants.ROLE_USER.getRoleName() + " not found");
                });

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .mail(request.getEmail())
                .roles(Collections.singleton(userRole))
                .build();

        userRepository.save(user);
        logger.info("User registered successfully: {}", request.getUsername());
    }

    /**
     * Authenticates a user and generates JWT and refresh tokens.
     *
     * @param request The authentication request containing username and password
     * @return AuthResponseDTO containing JWT token and refresh token
     * @throws IllegalArgumentException If the user is not found or credentials are invalid
     */
    public AuthResponseDTO login(AuthRequestDTO request) {
        if (request == null) {
            logger.error("Login failed: Request object is null");
            throw new IllegalArgumentException("Login request cannot be null");
        }

        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            logger.warn("Login failed: Username is null or empty");
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            logger.warn("Login failed: Password is null or empty");
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        logger.debug("Login attempt for user: {}", request.getUsername());

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    logger.warn("Login failed: User not found with username: {}", request.getUsername());
                    return new UserNotFoundException("User not found with username: " + request.getUsername());
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            logger.warn("Login failed: Invalid credentials for user: {}", request.getUsername());
            throw new InvalidCredentialsException("Invalid password for user: " + request.getUsername());
        }

        logger.debug("Generating JWT token for user: {}", user.getUsername());
        String jwt = jwtService.generateToken(user);

        logger.debug("Creating refresh token for user: {}", user.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        logger.info("User logged in successfully: {}", user.getUsername());
        return new AuthResponseDTO(jwt, refreshToken.getToken());
    }

    /**
     * Logs out a user by deleting their refresh tokens.
     *
     * @param user The user to log out
     */
    public void logout(User user) {
        if (user == null) {
            logger.error("Logout failed: User object is null");
            throw new IllegalArgumentException("User cannot be null");
        }

        logger.debug("Logging out user: {}", user.getUsername());
        refreshTokenService.deleteByUser(user);
        logger.info("User logged out successfully: {}", user.getUsername());
    }
}
