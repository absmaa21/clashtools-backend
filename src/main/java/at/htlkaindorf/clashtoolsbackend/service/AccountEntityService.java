package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.dto.account.AccountEntityDTO;
import at.htlkaindorf.clashtoolsbackend.dto.account.AccountEntityRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.account.SimplifiedAccountEntityDTO;
import at.htlkaindorf.clashtoolsbackend.mapper.AccountEntityMapper;
import at.htlkaindorf.clashtoolsbackend.mapper.SimplifiedAccountEntityMapper;
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
    private final SimplifiedAccountEntityMapper simplifiedAccountEntityMapper;

    /**
     * Retrieves all account entities for a specific account.
     *
     * @param accountId The ID of the account to retrieve entities for
     * @return A list of SimplifiedAccountEntityDTO objects representing the account entities
     */
    public List<SimplifiedAccountEntityDTO> getAllAccEntities(Long accountId) {
        List<AccountEntity> accountEntities = accountEntityRepository.findAllByAccountId(accountId);
        return simplifiedAccountEntityMapper.toDTOList(accountEntities);
    }

    /**
     * Retrieves a specific account entity by its ID.
     *
     * @param id The ID of the account entity to retrieve
     * @return A SimplifiedAccountEntityDTO representing the requested account entity
     * @throws IllegalArgumentException if no account entity with the given ID exists
     */
    public SimplifiedAccountEntityDTO getAccountEntityById(Long id) {
        AccountEntity accountEntity = accountEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("AccountEntity not found"));
        return simplifiedAccountEntityMapper.toDTO(accountEntity);
    }

    /**
     * Creates a new account entity.
     *
     * @param request The AccountEntityRequestDTO containing the data for the new account entity
     * @return A SimplifiedAccountEntityDTO representing the newly created account entity
     * @throws IllegalArgumentException if the account or base entity doesn't exist
     */
    public SimplifiedAccountEntityDTO createAccountEntity(AccountEntityRequestDTO request) {
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
        return simplifiedAccountEntityMapper.toDTO(savedAccountEntity);
    }

    /**
     * Updates an existing account entity.
     *
     * @param id The ID of the account entity to update
     * @param request The AccountEntityRequestDTO containing the updated data
     * @return A SimplifiedAccountEntityDTO representing the updated account entity
     * @throws IllegalArgumentException if the account entity, account, or base entity doesn't exist
     */
    public SimplifiedAccountEntityDTO updateAccountEntity(Long id, AccountEntityRequestDTO request) {
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
        return simplifiedAccountEntityMapper.toDTO(updatedAccountEntity);
    }

    /**
     * Updates the upgrade start time for an account entity.
     *
     * @param id The ID of the account entity to update
     * @param upgradeStart The new upgrade start time in milliseconds
     * @return A SimplifiedAccountEntityDTO representing the updated account entity
     * @throws IllegalArgumentException if the account entity doesn't exist
     */
    public SimplifiedAccountEntityDTO updateUpgradeStart(Long id, Integer upgradeStart) {
        AccountEntity accountEntity = accountEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("AccountEntity not found"));

        accountEntity.setUpgradeStart(upgradeStart);

        AccountEntity updatedAccountEntity = accountEntityRepository.save(accountEntity);
        return simplifiedAccountEntityMapper.toDTO(updatedAccountEntity);
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
