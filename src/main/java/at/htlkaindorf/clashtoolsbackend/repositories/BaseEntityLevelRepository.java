package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.Attribute;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntityLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing BaseEntityLevel entities.
 * Provides methods for CRUD operations and custom queries related to base entity levels.
 */
@Repository
public interface BaseEntityLevelRepository extends JpaRepository<BaseEntityLevel, Long> {

    /**
     * Find all base entity levels associated with a specific base entity
     *
     * @param baseEntity the base entity to search for
     * @return a list of base entity levels
     */
    List<BaseEntityLevel> findByBaseEntity(BaseEntity baseEntity);

    /**
     * Find all base entity levels associated with a specific base entity ID
     *
     * @param baseEntityId the ID of the base entity to search for
     * @return a list of base entity levels
     */
    List<BaseEntityLevel> findByBaseEntityId(Long baseEntityId);

    /**
     * Find all base entity levels associated with a specific level value
     *
     * @param level the level value to search for
     * @return a list of base entity levels
     */
    List<BaseEntityLevel> findByLevel(Integer level);

    /**
     * Find a base entity level by its base entity and level value
     *
     * @param baseEntity the base entity to search for
     * @param level the level value to search for
     * @return the base entity level if found
     */
    Optional<BaseEntityLevel> findByBaseEntityAndLevel(BaseEntity baseEntity, Integer level);

    /**
     * Find a base entity level by its base entity ID and level value
     *
     * @param baseEntityId the ID of the base entity to search for
     * @param level the level value to search for
     * @return the base entity level if found
     */
    Optional<BaseEntityLevel> findByBaseEntityIdAndLevel(Long baseEntityId, Integer level);

    /**
     * Find all base entity levels that have a specific attribute
     *
     * @param attribute the attribute to search for
     * @return a list of base entity levels
     */
    List<BaseEntityLevel> findByAttributesContaining(Attribute attribute);

    /**
     * Delete all base entity levels associated with a specific base entity
     *
     * @param baseEntity the base entity whose levels should be deleted
     */
    void deleteByBaseEntity(BaseEntity baseEntity);

    /**
     * Delete all base entity levels associated with a specific level value
     *
     * @param level the level value whose base entity levels should be deleted
     */
    void deleteByLevel(Integer level);
}
