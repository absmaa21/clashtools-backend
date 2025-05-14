package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.account.AccountRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.account.AccountResponseDTO;
import at.htlkaindorf.clashtoolsbackend.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Account Controller.
 * Provides REST API endpoints for managing user accounts in the system.
 * This controller handles CRUD operations (Create, Read, Update, Delete) for accounts,
 * which represent user accounts in the Clash Tools application.
 */
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * Get all accounts.
     * Retrieves a list of all accounts in the system.
     *
     * @return ResponseEntity containing a list of AccountResponseDTO objects
     */
    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAllAccounts() {
        List<AccountResponseDTO> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    /**
     * Get account by ID.
     * Retrieves a specific account identified by its unique ID.
     *
     * @param id The unique identifier of the account to retrieve
     * @return ResponseEntity containing the requested AccountResponseDTO
     * @throws IllegalArgumentException if no account with the given ID exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getAccountById(
            @PathVariable Long id) {
        AccountResponseDTO account = accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    /**
     * Get accounts by user ID.
     * Retrieves all accounts belonging to a specific user.
     *
     * @param userId The ID of the user to filter by
     * @return ResponseEntity containing a list of AccountResponseDTO objects
     */
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<AccountResponseDTO>> getAccountsByUserId(
            @PathVariable Long userId) {
        List<AccountResponseDTO> accounts = accountService.getAccountsByUserId(userId);
        return ResponseEntity.ok(accounts);
    }

    /**
     * Create a new account.
     * Creates a new account with the provided information. The request is validated
     * to ensure all required fields are present and valid.
     *
     * @param request The AccountRequestDTO containing the information for the new account
     * @return ResponseEntity containing the newly created AccountResponseDTO
     * @throws jakarta.validation.ConstraintViolationException if validation fails
     * @throws IllegalArgumentException if the user with the given ID does not exist
     * @throws IllegalArgumentException if an account with the given name already exists
     */
    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(
            @Valid @RequestBody AccountRequestDTO request) {
        AccountResponseDTO account = accountService.createAccount(request);
        return ResponseEntity.ok(account);
    }

    /**
     * Update an account.
     * Updates an existing account identified by its unique ID with the provided information.
     * The request is validated to ensure all required fields are present and valid.
     *
     * @param id The unique identifier of the account to update
     * @param request The AccountRequestDTO containing the updated information
     * @return ResponseEntity containing the updated AccountResponseDTO
     * @throws IllegalArgumentException if no account with the given ID exists
     * @throws IllegalArgumentException if the user with the given ID does not exist
     * @throws IllegalArgumentException if an account with the given name already exists (and it's not the current account)
     * @throws jakarta.validation.ConstraintViolationException if validation fails
     */
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> updateAccount(
            @PathVariable Long id,
            @Valid @RequestBody AccountRequestDTO request) {
        AccountResponseDTO account = accountService.updateAccount(id, request);
        return ResponseEntity.ok(account);
    }

    /**
     * Delete an account.
     * Deletes an account identified by its unique ID. If the entity doesn't exist,
     * the operation still returns a successful response.
     *
     * @param id The unique identifier of the account to delete
     * @return ResponseEntity with no content, indicating successful deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(
            @PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
