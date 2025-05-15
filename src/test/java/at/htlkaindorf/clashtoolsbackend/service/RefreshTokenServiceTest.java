package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.pojos.RefreshToken;
import at.htlkaindorf.clashtoolsbackend.pojos.User;
import at.htlkaindorf.clashtoolsbackend.repositories.RefreshTokenRepository;
import at.htlkaindorf.clashtoolsbackend.service.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @Captor
    private ArgumentCaptor<RefreshToken> refreshTokenCaptor;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Create a test user
        testUser = User.builder()
                .id(1L)
                .username("testuser")
                .password("password")
                .mail("test@example.com")
                .build();
    }

    @Test
    void testCreateRefreshToken() {
        // Arrange
        RefreshToken savedRefreshToken = RefreshToken.builder()
                .id(1L)
                .user(testUser)
                .token("generated-token")
                .expiryDate(Instant.now().plus(Duration.ofDays(7)))
                .build();

        when(refreshTokenRepository.save(any(RefreshToken.class))).thenReturn(savedRefreshToken);

        // Act
        RefreshToken result = refreshTokenService.createRefreshToken(testUser);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(testUser, result.getUser());
        assertEquals("generated-token", result.getToken());

        // Verify
        verify(refreshTokenRepository).deleteByUser(testUser);
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

    @Test
    void testCreateRefreshToken_VerifyTokenProperties() {
        // Arrange
        when(refreshTokenRepository.save(refreshTokenCaptor.capture())).thenAnswer(invocation -> {
            RefreshToken capturedToken = refreshTokenCaptor.getValue();
            capturedToken.setId(1L);
            return capturedToken;
        });

        // Act
        RefreshToken result = refreshTokenService.createRefreshToken(testUser);

        // Assert
        RefreshToken capturedToken = refreshTokenCaptor.getValue();
        assertNotNull(capturedToken.getToken());
        assertNotNull(capturedToken.getExpiryDate());
        assertEquals(testUser, capturedToken.getUser());

        // Verify token expiry is approximately 7 days in the future
        Instant now = Instant.now();
        Instant sevenDaysLater = now.plus(Duration.ofDays(7));
        // Allow 5 seconds tolerance for test execution time
        assertTrue(capturedToken.getExpiryDate().isAfter(sevenDaysLater.minus(Duration.ofSeconds(5))));
        assertTrue(capturedToken.getExpiryDate().isBefore(sevenDaysLater.plus(Duration.ofSeconds(5))));
    }

    @Test
    void testIsTokenExpired_WhenExpired() {
        // Arrange
        RefreshToken expiredToken = RefreshToken.builder()
                .id(1L)
                .user(testUser)
                .token("expired-token")
                .expiryDate(Instant.now().minus(Duration.ofDays(1)))
                .build();

        // Act
        boolean result = refreshTokenService.isTokenExpired(expiredToken);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsTokenExpired_WhenNotExpired() {
        // Arrange
        RefreshToken validToken = RefreshToken.builder()
                .id(1L)
                .user(testUser)
                .token("valid-token")
                .expiryDate(Instant.now().plus(Duration.ofDays(1)))
                .build();

        // Act
        boolean result = refreshTokenService.isTokenExpired(validToken);

        // Assert
        assertFalse(result);
    }

    @Test
    void testDeleteByUser() {
        // Act
        refreshTokenService.deleteByUser(testUser);

        // Verify
        verify(refreshTokenRepository).deleteByUser(testUser);
    }
}
