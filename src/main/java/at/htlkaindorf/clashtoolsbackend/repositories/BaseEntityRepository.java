package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Tag(name = "BaseEntityRepository", description = "Repository for managing BaseEntity objects")
public interface BaseEntityRepository extends JpaRepository<BaseEntity, Long> {
}
