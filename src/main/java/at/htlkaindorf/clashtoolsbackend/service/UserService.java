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
     * Find a user by their username
     *
     * @param username the username to search for
     * @return an Optional containing the user if found, or empty otherwise
     */
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Find a user by their email address
     *
     * @param mail the email address to search for
     * @return an Optional containing the user if found, or empty otherwise
     */
    public Optional<User> findUserByMail(String mail) {
        return userRepository.findByMail(mail);
    }

    /**
     * Save a user to the database
     *
     * @param user the user to save
     * @return the saved user with any generated IDs
     */
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
