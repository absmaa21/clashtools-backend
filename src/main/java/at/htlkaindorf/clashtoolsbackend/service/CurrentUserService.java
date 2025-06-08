package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.pojos.User;
import at.htlkaindorf.clashtoolsbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service for accessing the currently authenticated user.
 * Provides methods to retrieve the current user and user ID in a centralized way.
 */
@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    /**
     * Gets the currently authenticated user.
     *
     * @return The User entity of the currently authenticated user
     * @throws IllegalStateException if no authenticated user is found
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }

        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
    }

    /**
     * Gets the ID of the currently authenticated user.
     *
     * @return The ID of the currently authenticated user
     * @throws IllegalStateException if no authenticated user is found
     */
    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
