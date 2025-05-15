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
import at.htlkaindorf.clashtoolsbackend.repositories.RefreshTokenRepository;
import at.htlkaindorf.clashtoolsbackend.repositories.RoleRepository;
import at.htlkaindorf.clashtoolsbackend.repositories.UserRepository;
import at.htlkaindorf.clashtoolsbackend.service.AuthService;
import at.htlkaindorf.clashtoolsbackend.service.JwtService;
import at.htlkaindorf.clashtoolsbackend.service.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private RefreshTokenRepository refreshTokenRepository;
    private RefreshTokenService refreshTokenService;
    private RoleRepository roleRepository;
    private AuthService authService;

    private User testUser;
    private Role userRole;
    private RegisterRequestDTO registerRequest;
    private AuthRequestDTO authRequest;

    @BeforeEach
    void setUp() {
        // Create mocks
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        refreshTokenRepository = Mockito.mock(RefreshTokenRepository.class);

        // Create real instances
        jwtService = new JwtService();
        refreshTokenService = new RefreshTokenService(refreshTokenRepository);

        // Create the service under test
        authService = new AuthService(
            userRepository,
            passwordEncoder,
            jwtService,
            refreshTokenService,
            roleRepository
        );

        // Create a test role
        userRole = Role.builder()
                .id(1L)
                .name(RoleConstants.ROLE_USER.getRoleName())
                .build();

        // Create a test user
        testUser = User.builder()
                .id(1L)
                .username("testuser")
                .password("encodedPassword")
                .mail("test@example.com")
                .roles(Collections.singleton(userRole))
                .build();

        // Create a registration request
        registerRequest = new RegisterRequestDTO();
        registerRequest.setUsername("newuser");
        registerRequest.setPassword("Password1!");
        registerRequest.setEmail("new@example.com");

        // Create an authentication request
        authRequest = new AuthRequestDTO();
        authRequest.setUsername("testuser");
        authRequest.setPassword("password");
    }

    @Test
    void testRegister_Success() {
        // Arrange
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByMail("new@example.com")).thenReturn(false);
        when(roleRepository.findByName(RoleConstants.ROLE_USER.getRoleName())).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode("Password1!")).thenReturn("encodedPassword");

        // Act & Assert
        assertDoesNotThrow(() -> authService.register(registerRequest));

        // Verify
        verify(userRepository).existsByUsername("newuser");
        verify(userRepository).existsByMail("new@example.com");
        verify(roleRepository).findByName(RoleConstants.ROLE_USER.getRoleName());
        verify(passwordEncoder).encode("Password1!");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegister_UsernameAlreadyTaken() {
        // Arrange
        when(userRepository.existsByUsername("newuser")).thenReturn(true);

        // Act & Assert
        UsernameAlreadyExistsException exception = assertThrows(
                UsernameAlreadyExistsException.class,
                () -> authService.register(registerRequest)
        );
        assertEquals("Username already taken", exception.getMessage());

        // Verify
        verify(userRepository).existsByUsername("newuser");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegister_EmailAlreadyRegistered() {
        // Arrange
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByMail("new@example.com")).thenReturn(true);

        // Act & Assert
        EmailAlreadyExistsException exception = assertThrows(
                EmailAlreadyExistsException.class,
                () -> authService.register(registerRequest)
        );
        assertEquals("Email already registered", exception.getMessage());

        // Verify
        verify(userRepository).existsByUsername("newuser");
        verify(userRepository).existsByMail("new@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegister_RoleNotFound() {
        // Arrange
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByMail("new@example.com")).thenReturn(false);
        when(roleRepository.findByName(RoleConstants.ROLE_USER.getRoleName())).thenReturn(Optional.empty());

        // Act & Assert
        RoleNotFoundException exception = assertThrows(
                RoleNotFoundException.class,
                () -> authService.register(registerRequest)
        );
        assertEquals("Default role " + RoleConstants.ROLE_USER.getRoleName() + " not found", exception.getMessage());

        // Verify
        verify(userRepository).existsByUsername("newuser");
        verify(userRepository).existsByMail("new@example.com");
        verify(roleRepository).findByName(RoleConstants.ROLE_USER.getRoleName());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLogin_Success() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        // Create a refresh token that will be returned by the refreshTokenRepository
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refresh-token");
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenReturn(refreshToken);

        // Act
        AuthResponseDTO response = authService.login(authRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getAccessToken()); // We can't predict the exact token value
        assertEquals("refresh-token", response.getRefreshToken());

        // Verify
        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder).matches("password", "encodedPassword");
        verify(refreshTokenRepository).deleteByUser(testUser);
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

    @Test
    void testLogin_UserNotFound() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> authService.login(authRequest)
        );
        assertEquals("User not found with username: testuser", exception.getMessage());

        // Verify
        verify(userRepository).findByUsername("testuser");
        verify(refreshTokenRepository, never()).save(any());
    }

    @Test
    void testLogin_InvalidCredentials() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(false);

        // Act & Assert
        InvalidCredentialsException exception = assertThrows(
                InvalidCredentialsException.class,
                () -> authService.login(authRequest)
        );
        assertEquals("Invalid password for user: testuser", exception.getMessage());

        // Verify
        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder).matches("password", "encodedPassword");
        verify(refreshTokenRepository, never()).save(any());
    }

    @Test
    void testLogout() {
        // Act
        authService.logout(testUser);

        // Verify
        verify(refreshTokenRepository).deleteByUser(testUser);
    }
}
