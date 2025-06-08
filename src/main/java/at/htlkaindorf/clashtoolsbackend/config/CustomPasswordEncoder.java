package at.htlkaindorf.clashtoolsbackend.config;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

/**
 * Custom password encoder that uses PBKDF2 with HMAC SHA-256 to hash and verify passwords.
 * This replaces Spring Security's PasswordEncoder.
 */
@Component
public class CustomPasswordEncoder {

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Encodes the raw password using PBKDF2 with a random salt.
     *
     * @param rawPassword the raw password to encode
     * @return the encoded password (format: salt:hash)
     */
    public String encode(String rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        byte[] salt = new byte[SALT_LENGTH];
        RANDOM.nextBytes(salt);

        byte[] hash = hashPassword(rawPassword.toCharArray(), salt);

        // Format: base64(salt):base64(hash)
        return Base64.getEncoder().encodeToString(salt) + ":" +
               Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Verifies that the raw password matches the encoded password.
     *
     * @param rawPassword the raw password to check
     * @param encodedPassword the encoded password to compare with (format: salt:hash)
     * @return true if the passwords match, false otherwise
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }

        try {
            // Split the encoded password into salt and hash
            String[] parts = encodedPassword.split(":");
            if (parts.length != 2) {
                return false;
            }

            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] hash = Base64.getDecoder().decode(parts[1]);

            // Hash the raw password with the same salt
            byte[] testHash = hashPassword(rawPassword.toCharArray(), salt);

            // Compare the hashes
            return Arrays.equals(hash, testHash);
        } catch (Exception e) {
            return false;
        }
    }

    private byte[] hashPassword(char[] password, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            return factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
