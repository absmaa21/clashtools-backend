package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.pojos.User;
import at.htlkaindorf.clashtoolsbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * User service for finding users by username.
 * This replaces the Spring Security UserDetailsService.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {

    private final UserRepository userRepository;

    /**
     * Finds a user by username.
     *
     * @param username Username to search for
     * @return Optional containing the user if found
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
