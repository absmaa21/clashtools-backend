package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.pojos.User;
import at.htlkaindorf.clashtoolsbackend.repositories.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Tag(name = "UserService", description = "Service for managing users")
public class UserService {

    private final UserRepository userRepository;

    /**
     * Finds a user by username
     *
     * @param username Username to search for
     * @return Optional containing the user if found
     * @throws IllegalArgumentException if username is null
     */
    public Optional<User> findUserByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        return userRepository.findByUsername(username);
    }

    /**
     * Finds a user by email address
     *
     * @param mail Email address to search for
     * @return Optional containing the user if found
     * @throws IllegalArgumentException if email is null
     */
    public Optional<User> findUserByMail(String mail) {
        if (mail == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        return userRepository.findByMail(mail);
    }

    /**
     * Saves a user to the database
     *
     * @param user User to save
     * @return Saved user with generated ID
     * @throws IllegalArgumentException if user is null
     */
    @Transactional
    public User saveUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return userRepository.save(user);
    }
}
