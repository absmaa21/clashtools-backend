package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.ApiResponse;
import at.htlkaindorf.clashtoolsbackend.pojos.User;
import at.htlkaindorf.clashtoolsbackend.service.CurrentUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for retrieving information about the currently authenticated user.
 * Provides endpoints to demonstrate how to access user information.
 */
@RestController
@RequestMapping("/api/user-info")
@RequiredArgsConstructor
@Tag(name = "User Info", description = "API for retrieving information about the current user")
public class UserInfoController {

    private final CurrentUserService currentUserService;

    /**
     * Get the ID of the currently authenticated user using the CurrentUserService.
     *
     * @return ResponseEntity containing the user ID
     */
    @GetMapping("/current-user-id")
    @Operation(summary = "Get current user ID", description = "Retrieves the ID of the currently authenticated user using CurrentUserService")
    public ResponseEntity<ApiResponse<Long>> getCurrentUserId() {
        Long userId = currentUserService.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success(userId, "Current user ID retrieved successfully"));
    }

    /**
     * Get information about the currently authenticated user using the CurrentUserService.
     *
     * @return ResponseEntity containing user information
     */
    @GetMapping("/current-user")
    @Operation(summary = "Get current user info", description = "Retrieves information about the currently authenticated user")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCurrentUserInfo() {
        User user = currentUserService.getCurrentUser();

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", user.getId());
        userInfo.put("username", user.getUsername());
        userInfo.put("email", user.getMail());
        userInfo.put("roles", user.getRoles());

        return ResponseEntity.ok(ApiResponse.success(userInfo, "Current user information retrieved successfully"));
    }
}
