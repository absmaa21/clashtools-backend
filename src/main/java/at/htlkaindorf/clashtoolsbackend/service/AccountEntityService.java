package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.dto.account.AccountEntityDTO;
import at.htlkaindorf.clashtoolsbackend.dto.account.AccountEntityRequestDTO;
import at.htlkaindorf.clashtoolsbackend.mapper.AccountEntityMapper;
import at.htlkaindorf.clashtoolsbackend.pojos.Account;
import at.htlkaindorf.clashtoolsbackend.pojos.AccountEntity;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import at.htlkaindorf.clashtoolsbackend.repositories.AccountEntityRepository;
import at.htlkaindorf.clashtoolsbackend.repositories.AccountRepository;
import at.htlkaindorf.clashtoolsbackend.repositories.BaseEntityRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing account entities in the system.
 * This service provides methods for creating, retrieving, updating, and deleting account entities,
 * which represent game entities owned by user accounts in the Clash Tools application.
 */
@Service
@RequiredArgsConstructor
@Tag(name = "AccountEntityService", description = "Service for managing account entities")
public class AccountEntityService {

    private final AccountEntityRepository accountEntityRepository;
    private final AccountRepository accountRepository;
    private final BaseEntityRepository baseEntityRepository;
    private final AccountEntityMapper accountEntityMapper;

    /**
     * Retrieves all account entities for a specific account.
     *
     * @param accountId The ID of the account to retrieve entities for
     * @return A list of AccountEntityDTO objects representing the account entities
     */
    public List<AccountEntityDTO> getAllAccEntities(Long accountId) {
        List<AccountEntity> accountEntities = accountEntityRepository.findAllByAccountId(accountId);
        return accountEntities.stream().map(accountEntityMapper::toDTO).toList();
    }

    /**
     * Retrieves a specific account entity by its ID.
     *
     * @param id The ID of the account entity to retrieve
     * @return An AccountEntityDTO representing the requested account entity
     * @throws IllegalArgumentException if no account entity with the given ID exists
     */
    public AccountEntityDTO getAccountEntityById(Long id) {
        AccountEntity accountEntity = accountEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("AccountEntity not found"));
        return accountEntityMapper.toDTO(accountEntity);
    }

    /**
     * Creates a new account entity.
     *
     * @param request The AccountEntityRequestDTO containing the data for the new account entity
     * @return An AccountEntityDTO representing the newly created account entity
     * @throws IllegalArgumentException if the account or base entity doesn't exist
     */
    public AccountEntityDTO createAccountEntity(AccountEntityRequestDTO request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        BaseEntity baseEntity = baseEntityRepository.findById(request.getBaseEntityId())
                .orElseThrow(() -> new IllegalArgumentException("BaseEntity not found"));

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccount(account);
        accountEntity.setBaseEntity(baseEntity);
        accountEntity.setCurrentLevel(request.getCurrentLevel());
        accountEntity.setUpgradeStart(request.getUpgradeStart());

        AccountEntity savedAccountEntity = accountEntityRepository.save(accountEntity);
        return accountEntityMapper.toDTO(savedAccountEntity);
    }

    /**
     * Updates an existing account entity.
     *
     * @param id The ID of the account entity to update
     * @param request The AccountEntityRequestDTO containing the updated data
     * @return An AccountEntityDTO representing the updated account entity
     * @throws IllegalArgumentException if the account entity, account, or base entity doesn't exist
     */
    public AccountEntityDTO updateAccountEntity(Long id, AccountEntityRequestDTO request) {
        AccountEntity accountEntity = accountEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("AccountEntity not found"));

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        BaseEntity baseEntity = baseEntityRepository.findById(request.getBaseEntityId())
                .orElseThrow(() -> new IllegalArgumentException("BaseEntity not found"));

        accountEntity.setAccount(account);
        accountEntity.setBaseEntity(baseEntity);
        accountEntity.setCurrentLevel(request.getCurrentLevel());
        accountEntity.setUpgradeStart(request.getUpgradeStart());

        AccountEntity updatedAccountEntity = accountEntityRepository.save(accountEntity);
        return accountEntityMapper.toDTO(updatedAccountEntity);
    }

    /**
     * Updates the upgrade start time for an account entity.
     *
     * @param id The ID of the account entity to update
     * @param upgradeStart The new upgrade start time in milliseconds
     * @return An AccountEntityDTO representing the updated account entity
     * @throws IllegalArgumentException if the account entity doesn't exist
     */
    public AccountEntityDTO updateUpgradeStart(Long id, Integer upgradeStart) {
        AccountEntity accountEntity = accountEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("AccountEntity not found"));

        accountEntity.setUpgradeStart(upgradeStart);

        AccountEntity updatedAccountEntity = accountEntityRepository.save(accountEntity);
        return accountEntityMapper.toDTO(updatedAccountEntity);
    }

    /**
     * Deletes an account entity.
     *
     * @param id The ID of the account entity to delete
     */
    public void deleteAccountEntity(Long id) {
        accountEntityRepository.deleteById(id);
    }
}
