package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing AccountEntity objects.
 * Provides methods for CRUD operations and custom queries related to account entities.
 */
@Repository
public interface AccountEntityRepository extends JpaRepository<AccountEntity, Long> {

    /**
     * Finds all account entities associated with a specific account.
     *
     * @param accountId The ID of the account to find entities for
     * @return A list of AccountEntity objects belonging to the specified account
     */
    List<AccountEntity> findAllByAccountId(Long accountId);

}
