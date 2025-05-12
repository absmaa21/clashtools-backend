package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseEntityRepository extends JpaRepository<BaseEntity, Long> {
    List<BaseEntity> findByAccountId(Long accountId);
}
