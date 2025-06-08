package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.pojos.User;
import at.htlkaindorf.clashtoolsbackend.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Service for accessing the currently authenticated user.
 * Provides methods to retrieve the current user and user ID in a centralized way.
 */
@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    /**
     * Gets the currently authenticated user from the request attribute.
     *
     * @return The User entity of the currently authenticated user
     * @throws IllegalStateException if no authenticated user is found
     */
    public User getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        User user = (User) request.getAttribute("currentUser");

        if (user == null) {
            throw new IllegalStateException("No authenticated user found");
        }

        return user;
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
