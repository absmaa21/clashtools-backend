package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Tag(name = "UserRepository", description = "Repository for managing User objects")
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find a user by their username
     *
     * @param username the username to search for
     * @return the user if found, or empty optional otherwise
     */
    Optional<User> findByUsername(String username);

    /**
     * Check if a user with the given username exists
     *
     * @param username the username to check
     * @return true if a user with the username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if a user with the given email exists
     *
     * @param mail the email to check
     * @return true if a user with the email exists, false otherwise
     */
    boolean existsByMail(String mail);

    /**
     * Find a user by their email address
     *
     * @param mail the email to search for
     * @return the user if found, or empty optional otherwise
     */
    Optional<User> findByMail(String mail);
}
