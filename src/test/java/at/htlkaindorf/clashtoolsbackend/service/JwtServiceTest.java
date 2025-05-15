package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.pojos.Role;
import at.htlkaindorf.clashtoolsbackend.pojos.User;
import at.htlkaindorf.clashtoolsbackend.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private JwtService jwtService;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Initialize the JwtService
        jwtService = new JwtService();

        // Create a test role
        Role userRole = Role.builder()
                .name("ROLE_USER")
                .build();

        // Create a test user with the role
        testUser = User.builder()
                .username("testuser")
                .password("password")
                .mail("test@example.com")
                .roles(Set.of(userRole))
                .build();
    }

    @Test
    void testGenerateAndValidateToken() {
        // Generate a token for the test user
        String token = jwtService.generateToken(testUser);

        // Token should not be null or empty
        assertNotNull(token);
        assertFalse(token.isEmpty());

        System.out.println("[DEBUG_LOG] Generated token: " + token);

        // Token should be valid
        boolean isValid = jwtService.validateToken(token);
        assertTrue(isValid, "Token should be valid");

        // Extract username from token
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(testUser.getUsername(), extractedUsername,
                "Extracted username should match the original username");
    }

    @Test
    void testInvalidToken() {
        // Test with an invalid token
        String invalidToken = "invalid.token.string";
        boolean isValid = jwtService.validateToken(invalidToken);
        assertFalse(isValid, "Invalid token should not be validated");
    }
}
