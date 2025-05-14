package at.htlkaindorf.clashtoolsbackend.pojos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "app_user")
@Schema(description = "Entity representing a user in the application")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 10)
    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    @Version
    @Schema(description = "Version for optimistic locking", example = "0")
    private Long version;

    @Column(nullable = false, unique = true)
    @Schema(description = "Username for login", example = "johndoe", required = true)
    private String username;

    @Column(nullable = false)
    @Schema(description = "Encrypted password", example = "$2a$10$...", required = true)
    private String password;

    @Column(nullable = false, unique = true)
    @Email
    @Schema(description = "Email address of the user", example = "john.doe@example.com", required = true)
    private String mail;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Schema(description = "Roles assigned to the user")
    private Set<Role> roles;


    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    @Schema(description = "Accounts owned by the user")
    private Set<Account> accounts;

    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    @Schema(description = "Refresh tokens associated with the user")
    private Set<RefreshToken> refreshTokens;
}
