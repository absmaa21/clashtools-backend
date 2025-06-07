package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.dto.account.AccountRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.account.AccountResponseDTO;
import at.htlkaindorf.clashtoolsbackend.mapper.AccountMapper;
import at.htlkaindorf.clashtoolsbackend.pojos.Account;
import at.htlkaindorf.clashtoolsbackend.pojos.User;
import at.htlkaindorf.clashtoolsbackend.repositories.AccountRepository;
import at.htlkaindorf.clashtoolsbackend.repositories.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing user accounts.
 * This service provides methods for creating, retrieving, updating, and deleting accounts,
 * which represent user accounts in the Clash Tools application.
 * It extends AbstractCrudService to inherit common CRUD operations.
 */
@Service
@Tag(name = "AccountService", description = "Service for managing user accounts")
public class AccountService extends AbstractCrudService<Account, AccountResponseDTO, AccountRequestDTO, Long> {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository, AccountMapper accountMapper) {
        super(accountRepository, accountMapper);
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    protected void setEntityId(Account entity, Long id) {
        entity.setId(id);
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
     * This method overrides the default implementation to add validation and handle relationships.
     *
     * @param request The AccountRequestDTO containing the data for the new account
     * @return An AccountResponseDTO representing the newly created account
     * @throws IllegalArgumentException If the user with the given ID does not exist
     * @throws IllegalArgumentException If an account with the given name already exists
     */
    @Override
    public AccountResponseDTO create(AccountRequestDTO request) {
        if (accountRepository.existsByAccountName(request.getAccountName())) {
            throw new IllegalArgumentException("Account name already exists");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Account account = mapper.toEntity(request);
        account.setUser(user);

        Account savedAccount = repository.save(account);
        return mapper.toDTO(savedAccount);
    }

    /**
     * Updates an existing account in the database.
     * This method overrides the default implementation to add validation and handle relationships.
     *
     * @param id The unique identifier of the account to update
     * @param request The AccountRequestDTO containing the updated data
     * @return An AccountResponseDTO representing the updated account
     * @throws IllegalArgumentException If no account with the given ID exists in the database
     * @throws IllegalArgumentException If the user with the given ID does not exist
     * @throws IllegalArgumentException If an account with the given name already exists (and it's not the current account)
     */
    @Override
    public AccountResponseDTO update(Long id, AccountRequestDTO request) {
        Account account = repository.findById(id)
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

        // Create a new entity from the request and set its ID
        Account updatedAccount = mapper.toEntity(request);
        setEntityId(updatedAccount, id);
        updatedAccount.setUser(account.getUser()); // Preserve the user relationship

        return mapper.toDTO(repository.save(updatedAccount));
    }
}
