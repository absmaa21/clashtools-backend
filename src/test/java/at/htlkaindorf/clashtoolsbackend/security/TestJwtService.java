package at.htlkaindorf.clashtoolsbackend.security;

import at.htlkaindorf.clashtoolsbackend.pojos.User;
import at.htlkaindorf.clashtoolsbackend.service.JwtService;

/**
 * A test implementation of JwtService that allows controlling the behavior
 * of extractUsername and validateToken methods for testing purposes.
 */
public class TestJwtService extends JwtService {

    private String extractedUsername;
    private boolean validationResult;

    /**
     * Sets the username that will be returned by extractUsername method.
     *
     * @param username The username to return
     */
    public void setExtractedUsername(String username) {
        this.extractedUsername = username;
    }

    /**
     * Sets the result that will be returned by validateToken method.
     *
     * @param result The validation result to return
     */
    public void setValidationResult(boolean result) {
        this.validationResult = result;
    }

    /**
     * Returns the pre-configured username regardless of the token.
     *
     * @param token The JWT token (ignored in this implementation)
     * @return The pre-configured username
     */
    @Override
    public String extractUsername(String token) {
        return extractedUsername;
    }

    /**
     * Returns the pre-configured validation result regardless of the token.
     *
     * @param token The JWT token (ignored in this implementation)
     * @return The pre-configured validation result
     */
    @Override
    public boolean validateToken(String token) {
        return validationResult;
    }

    /**
     * This method is not used in tests but is implemented to satisfy the interface.
     *
     * @param user The user for whom to generate a token
     * @return A dummy token
     */
    @Override
    public String generateToken(User user) {
        return "test.token.string";
    }
}
