package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.ApiResponse;
import at.htlkaindorf.clashtoolsbackend.dto.account.AccountEntityDTO;
import at.htlkaindorf.clashtoolsbackend.dto.account.AccountEntityRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.account.SimplifiedAccountEntityDTO;
import at.htlkaindorf.clashtoolsbackend.service.AccountEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing account entities.
 * Provides REST API endpoints for managing account entities in the system.
 */
@RestController
@RequestMapping("/api/account-entity")
@RequiredArgsConstructor
@Tag(name = "Account Entity", description = "API for managing account entities")
public class AccountEntityController {

    private final AccountEntityService accountEntityService;

    /**
     * Get all account entities for a specific account.
     *
     * @param accountId The ID of the account to retrieve entities for
     * @return ResponseEntity containing a list of SimplifiedAccountEntityDTO objects
     */
    @GetMapping("/account/{accountId}")
    @Operation(summary = "Get all account entities for a specific account")
    public ResponseEntity<ApiResponse<List<SimplifiedAccountEntityDTO>>> getAccEntitiesByAccountId(
            @PathVariable Long accountId
    ) {
        List<SimplifiedAccountEntityDTO> accountEntityDTOS = accountEntityService.getAllAccEntities(accountId);
        return ResponseEntity.ok(ApiResponse.success(accountEntityDTOS));
    }

    /**
     * Get a specific account entity by ID.
     *
     * @param id The ID of the account entity to retrieve
     * @return ResponseEntity containing the requested SimplifiedAccountEntityDTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a specific account entity by ID")
    public ResponseEntity<ApiResponse<SimplifiedAccountEntityDTO>> getAccountEntityById(
            @PathVariable Long id
    ) {
        SimplifiedAccountEntityDTO accountEntityDTO = accountEntityService.getAccountEntityById(id);
        return ResponseEntity.ok(ApiResponse.success(accountEntityDTO));
    }

    /**
     * Create a new account entity.
     *
     * @param request The AccountEntityRequestDTO containing the data for the new account entity
     * @return ResponseEntity containing the newly created SimplifiedAccountEntityDTO
     */
    @PostMapping
    @Operation(summary = "Create a new account entity")
    public ResponseEntity<ApiResponse<SimplifiedAccountEntityDTO>> createAccountEntity(
            @Valid @RequestBody AccountEntityRequestDTO request
    ) {
        SimplifiedAccountEntityDTO accountEntityDTO = accountEntityService.createAccountEntity(request);
        return ResponseEntity.ok(ApiResponse.success(accountEntityDTO, "Account entity created successfully"));
    }

    /**
     * Update an existing account entity.
     *
     * @param id The ID of the account entity to update
     * @param request The AccountEntityRequestDTO containing the updated data
     * @return ResponseEntity containing the updated SimplifiedAccountEntityDTO
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing account entity")
    public ResponseEntity<ApiResponse<SimplifiedAccountEntityDTO>> updateAccountEntity(
            @PathVariable Long id,
            @Valid @RequestBody AccountEntityRequestDTO request
    ) {
        SimplifiedAccountEntityDTO accountEntityDTO = accountEntityService.updateAccountEntity(id, request);
        return ResponseEntity.ok(ApiResponse.success(accountEntityDTO, "Account entity updated successfully"));
    }

    /**
     * Update the upgrade start time for an account entity.
     *
     * @param id The ID of the account entity to update
     * @param upgradeStart The new upgrade start time in milliseconds
     * @return ResponseEntity containing the updated SimplifiedAccountEntityDTO
     */
    @PatchMapping("/{id}/upgrade-start")
    @Operation(summary = "Update the upgrade start time for an account entity")
    public ResponseEntity<ApiResponse<SimplifiedAccountEntityDTO>> updateUpgradeStart(
            @PathVariable Long id,
            @RequestParam Integer upgradeStart
    ) {
        SimplifiedAccountEntityDTO accountEntityDTO = accountEntityService.updateUpgradeStart(id, upgradeStart);
        return ResponseEntity.ok(ApiResponse.success(accountEntityDTO, "Upgrade start time updated successfully"));
    }

    /**
     * Delete an account entity.
     *
     * @param id The ID of the account entity to delete
     * @return ResponseEntity with no content, indicating successful deletion
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an account entity")
    public ResponseEntity<ApiResponse<Void>> deleteAccountEntity(
            @PathVariable Long id
    ) {
        accountEntityService.deleteAccountEntity(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Account entity deleted successfully"));
    }
}
