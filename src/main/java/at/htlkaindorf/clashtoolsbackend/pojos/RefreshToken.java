package at.htlkaindorf.clashtoolsbackend.pojos;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Entity representing a refresh token for authentication.
 * Refresh tokens are used to obtain new access tokens without requiring the user to log in again.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    /**
     * The unique identifier for the refresh token.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_seq")
    @SequenceGenerator(name = "refresh_token_seq", sequenceName = "refresh_token_seq", allocationSize = 10)
    private Long id;

    /**
     * The user associated with this refresh token.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The unique token string used for authentication.
     */
    @Column(nullable = false, unique = true)
    private String token;

    /**
     * The timestamp when this token expires.
     */
    @Column(nullable = false)
    private Instant expiryDate;
}
