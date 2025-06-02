package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountEntityRepository extends JpaRepository<AccountEntity, Long> {

    @Query("SELECT id, account, baseEntity, currentLevel, upgradeStart FROM AccountEntity WHERE account.id = ?1")
    List<AccountEntity> findAllByAccountId(Long accountId);

}
