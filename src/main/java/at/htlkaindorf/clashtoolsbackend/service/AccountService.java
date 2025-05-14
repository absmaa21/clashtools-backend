package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.dto.account.AccountRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.account.AccountResponseDTO;
import at.htlkaindorf.clashtoolsbackend.mapper.AccountMapper;
import at.htlkaindorf.clashtoolsbackend.pojos.Account;
import at.htlkaindorf.clashtoolsbackend.pojos.User;
import at.htlkaindorf.clashtoolsbackend.repositories.AccountRepository;
import at.htlkaindorf.clashtoolsbackend.repositories.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing user accounts.
 * This service provides methods for creating, retrieving, updating, and deleting accounts,
 * which represent user accounts in the Clash Tools application.
 * It acts as an intermediary between the controller layer and the repository layer,
 * handling business logic and data transformation.
 */
@Service
@RequiredArgsConstructor
@Tag(name = "AccountService", description = "Service for managing user accounts")
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;

    /**
     * Retrieves all accounts from the database.
     * This method fetches all accounts stored in the system and converts them to DTOs
     * for use in the presentation layer.
     *
     * @return A list of AccountResponseDTO objects representing all accounts in the system
     */
    public List<AccountResponseDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accountMapper.toDTOList(accounts);
    }

    /**
     * Retrieves a specific account by its unique identifier.
     * This method searches for an account with the given ID in the database,
     * throws an exception if not found, and converts it to a DTO for the presentation layer.
     *
     * @param id The unique identifier of the account to retrieve
     * @return An AccountResponseDTO representing the requested account
     * @throws IllegalArgumentException If no account with the given ID exists in the database
     */
    public AccountResponseDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return accountMapper.toDTO(account);
    }

    /**
     * Retrieves all accounts belonging to a specific user.
     * This method fetches all accounts associated with the given user ID and converts them to DTOs.
     *
     * @param userId The ID of the user to filter by
     * @return A list of AccountResponseDTO objects representing accounts owned by the specified user
     */
    public List<AccountResponseDTO> getAccountsByUserId(Long userId) {
        List<Account> accounts = accountRepository.findByUserId(userId);
        return accountMapper.toDTOList(accounts);
    }

    /**
     * Creates a new account in the database.
     * This method converts the provided request DTO to an entity object,
     * persists it to the database, and returns the created entity as a DTO.
     *
     * @param request The AccountRequestDTO containing the data for the new account
     * @return An AccountResponseDTO representing the newly created account
     * @throws IllegalArgumentException If the user with the given ID does not exist
     * @throws IllegalArgumentException If an account with the given name already exists
     */
    public AccountResponseDTO createAccount(AccountRequestDTO request) {
        if (accountRepository.existsByAccountName(request.getAccountName())) {
            throw new IllegalArgumentException("Account name already exists");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Account account = accountMapper.toEntity(request);
        account.setUser(user);

        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDTO(savedAccount);
    }

    /**
     * Updates an existing account in the database.
     * This method retrieves the account with the given ID, updates its properties
     * with the values from the request DTO, persists the changes to the database,
     * and returns the updated entity as a DTO.
     *
     * @param id The unique identifier of the account to update
     * @param request The AccountRequestDTO containing the updated data
     * @return An AccountResponseDTO representing the updated account
     * @throws IllegalArgumentException If no account with the given ID exists in the database
     * @throws IllegalArgumentException If the user with the given ID does not exist
     * @throws IllegalArgumentException If an account with the given name already exists (and it's not the current account)
     */
    public AccountResponseDTO updateAccount(Long id, AccountRequestDTO request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // Check if the new account name is already taken by another account
        if (!account.getAccountName().equals(request.getAccountName()) &&
                accountRepository.existsByAccountName(request.getAccountName())) {
            throw new IllegalArgumentException("Account name already exists");
        }

        // Check if the user exists if the user ID is being changed
        if (!account.getUser().getId().equals(request.getUserId())) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            account.setUser(user);
        }

        account.setAccountName(request.getAccountName());

        Account updatedAccount = accountRepository.save(account);
        return accountMapper.toDTO(updatedAccount);
    }

    /**
     * Deletes an account from the database.
     * This method removes the account with the given ID from the database.
     * If no entity with the given ID exists, the operation completes silently.
     *
     * @param id The unique identifier of the account to delete
     */
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
