package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.config.CustomPasswordEncoder;
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
    private final CustomPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final RoleRepository roleRepository; // Used for default role assignment

    /**
     * Registers a new user in the system.
     *
     * @param request Registration request with username, email, and password
     * @throws IllegalArgumentException If request is invalid or username/email already exists
     * @throws RoleNotFoundException If the default user role is not found
     */
    @Transactional
    public void register(RegisterRequestDTO request) {
        validateNotNull(request, "Registration request");

        String username = validateNotEmpty(request.getUsername(), "Username");
        String email = validateNotEmpty(request.getEmail(), "Email");
        String password = validateNotEmpty(request.getPassword(), "Password");

        logger.debug("Attempting to register new user: {}", username);

        if (userRepository.existsByUsername(username)) {
            logger.warn("Registration failed: Username '{}' already taken", username);
            throw new UsernameAlreadyExistsException("Username already taken");
        }

        if (userRepository.existsByMail(email)) {
            logger.warn("Registration failed: Email '{}' already registered", email);
            throw new EmailAlreadyExistsException("Email already registered");
        }

        Role userRole = roleRepository.findByName(RoleConstants.ROLE_USER.getRoleName())
                .orElseThrow(() -> {
                    logger.error("Default role {} not found", RoleConstants.ROLE_USER.getRoleName());
                    return new RoleNotFoundException("Default role " + RoleConstants.ROLE_USER.getRoleName() + " not found");
                });

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .mail(email)
                .roles(Collections.singleton(userRole))
                .build();

        userRepository.save(user);
        logger.info("User registered successfully: {}", username);
    }

    /**
     * Validates that an object is not null
     *
     * @param obj Object to validate
     * @param fieldName Name of the field for error message
     * @throws IllegalArgumentException if the object is null
     */
    private void validateNotNull(Object obj, String fieldName) {
        if (obj == null) {
            logger.error("{} cannot be null", fieldName);
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
    }

    /**
     * Validates that a string is not null or empty
     *
     * @param value String to validate
     * @param fieldName Name of the field for error message
     * @return The trimmed string value
     * @throws IllegalArgumentException if the string is null or empty
     */
    private String validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            logger.warn("{} cannot be null or empty", fieldName);
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
        return value.trim();
    }

    /**
     * Authenticates a user and generates JWT and refresh tokens.
     *
     * @param request Authentication request with username and password
     * @return AuthResponseDTO containing JWT token and refresh token
     * @throws IllegalArgumentException If request is invalid
     * @throws UserNotFoundException If user not found
     * @throws InvalidCredentialsException If password is invalid
     */
    public AuthResponseDTO login(AuthRequestDTO request) {
        validateNotNull(request, "Login request");

        String username = validateNotEmpty(request.getUsername(), "Username");
        String password = validateNotEmpty(request.getPassword(), "Password");

        logger.debug("Login attempt for user: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.warn("Login failed: User not found: {}", username);
                    return new UserNotFoundException("User not found: " + username);
                });

        if (!passwordEncoder.matches(password, user.getPassword())) {
            logger.warn("Login failed: Invalid credentials for: {}", username);
            throw new InvalidCredentialsException("Invalid password for: " + username);
        }

        String jwt = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        logger.info("User logged in successfully: {}", username);
        return new AuthResponseDTO(jwt, refreshToken.getToken());
    }

    /**
     * Logs out a user by deleting their refresh tokens.
     *
     * @param user User to log out
     * @throws IllegalArgumentException If user is null
     */
    public void logout(User user) {
        validateNotNull(user, "User");

        String username = user.getUsername();
        logger.debug("Logging out user: {}", username);

        refreshTokenService.deleteByUser(user);
        logger.info("User logged out successfully: {}", username);
    }
}
