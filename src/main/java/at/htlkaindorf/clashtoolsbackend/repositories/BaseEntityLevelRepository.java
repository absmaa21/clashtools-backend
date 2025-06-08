package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntityLevel;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing BaseEntityLevel entities.
 * Provides methods for CRUD operations and custom queries related to base entity levels.
 */
@Repository
@Tag(name = "BaseEntityLevelRepository", description = "Repository for managing BaseEntityLevel objects")
public interface BaseEntityLevelRepository extends JpaRepository<BaseEntityLevel, Long> {
    /**
     * Find all base entity levels associated with a specific base entity ID
     *
     * @param baseEntityId the ID of the base entity to search for
     * @return a list of base entity levels
     */
    List<BaseEntityLevel> findByBaseEntityId(Long baseEntityId);

    /**
     * Find a base entity level by its base entity and level value
     *
     * @param baseEntity the base entity to search for
     * @param level the level value to search for
     * @return the base entity level if found
     */
    Optional<BaseEntityLevel> findByBaseEntityAndLevel(BaseEntity baseEntity, Integer level);

    /**
     * Delete all base entity levels associated with a specific base entity
     *
     * @param baseEntity the base entity whose levels should be deleted
     */
    void deleteByBaseEntity(BaseEntity baseEntity);

}
