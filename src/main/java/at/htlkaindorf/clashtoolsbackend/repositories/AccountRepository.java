package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.Account;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Tag(name = "AccountRepository", description = "Repository for managing Account objects")
public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * Check if an account with the given name exists
     *
     * @param accountName the name to check
     * @return true if an account with the name exists, false otherwise
     */
    boolean existsByAccountName(String accountName);

    /**
     * Find all accounts belonging to a specific user
     *
     * @param userId the ID of the user
     * @return list of accounts associated with the user
     */
    List<Account> findByUserId(Long userId);
}
