package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import at.htlkaindorf.clashtoolsbackend.repositories.projections.BaseEntitySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing BaseEntity entities in the database.
 * This interface extends JpaRepository to inherit common CRUD operations
 * and defines custom query methods for specific BaseEntity operations.
 */
@Repository
public interface BaseEntityRepository extends JpaRepository<BaseEntity, Long> {

    /**
     * Find all base entities with the specified category.
     *
     * @param category The category to filter by
     * @return A list of BaseEntity objects with the specified category
     */
    List<BaseEntity> findByCategory(Category category);

    /**
     * Find all base entities with names containing the specified string (case-insensitive).
     *
     * @param name The name substring to search for
     * @return A list of BaseEntity objects with matching names
     */
    List<BaseEntity> findByNameContainingIgnoreCase(String name);

    /**
     * Find a specific base entity by its ID with its baseEntityLevels eagerly loaded.
     * This query uses a join fetch to load the baseEntityLevels in a single query.
     *
     * @param id The ID of the base entity to find
     * @return The BaseEntity object with the specified ID, or null if not found
     */
    @Query("SELECT be FROM BaseEntity be LEFT JOIN FETCH be.baseEntityLevels WHERE be.id = :id")
    BaseEntity findByIdWithLevels(@Param("id") Long id);

    /**
     * Find all base entities with their baseEntityLevels eagerly loaded.
     * This query uses a join fetch to load the baseEntityLevels in a single query.
     * It also explicitly selects the category field to ensure it's properly loaded.
     *
     * @return A list of BaseEntity objects with their baseEntityLevels eagerly loaded
     */
    @Query("SELECT be, be.category FROM BaseEntity be LEFT JOIN FETCH be.baseEntityLevels")
    List<BaseEntity> findAllWithLevels();

    /**
     * Find all base entities and return them as BaseEntitySummary projections.
     *
     * @return A list of BaseEntitySummary projections
     */
    List<BaseEntitySummary> findAllProjectedBy();

    /**
     * Find all base entities with the specified category and return them as BaseEntitySummary projections.
     *
     * @param category The category to filter by
     * @return A list of BaseEntitySummary projections with the specified category
     */
    List<BaseEntitySummary> findSummaryByCategory(Category category);
}
