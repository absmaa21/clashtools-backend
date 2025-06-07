package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.ApiResponse;
import at.htlkaindorf.clashtoolsbackend.service.CurrentUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
