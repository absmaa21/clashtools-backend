package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.account.AccountRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.account.AccountResponseDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.Account;
import at.htlkaindorf.clashtoolsbackend.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Account Controller.
 * Provides REST API endpoints for managing user accounts in the system.
 * This controller extends CrudController to inherit common CRUD operations.
 */
@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Accounts", description = "API for managing user accounts")
public class AccountController extends CrudController<Account, AccountResponseDTO, AccountRequestDTO, Long> {

    /**
     * The service responsible for account-related operations.
     */
    private final AccountService accountService;

    /**
     * Constructor for AccountController.
     * Initializes the controller with the required service and passes it to the parent class.
     *
     * @param accountService The service for account operations
     */
    public AccountController(AccountService accountService) {
        super(accountService);
        this.accountService = accountService;
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

}
