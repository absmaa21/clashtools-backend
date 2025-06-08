package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import at.htlkaindorf.clashtoolsbackend.repositories.projections.BaseEntitySummary;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Tag(name = "BaseEntityRepository", description = "Repository for managing BaseEntity objects")
public interface BaseEntityRepository extends JpaRepository<BaseEntity, Long> {

    /**
     * Check if a base entity with the given name exists.
     *
     * @param name The name to check
     * @return true if a base entity with the given name exists, false otherwise
     */
    boolean existsByName(String name);

    /**
     * Check if a base entity with the given name exists and has a different ID.
     * This is useful for validating updates to ensure uniqueness while allowing
     * an entity to keep its own name.
     *
     * @param name The name to check
     * @param id The ID to exclude from the check
     * @return true if a base entity with the given name exists and has a different ID, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(be) > 0 THEN true ELSE false END FROM BaseEntity be WHERE be.name = :name AND be.id <> :id")
    boolean existsByNameAndIdNot(String name, Long id);

    /**
     * Find all base entities by category.
     * This method uses the index on the category field for efficient querying.
     *
     * @param category The category to search for
     * @return A list of BaseEntity objects with the specified category
     */
    List<BaseEntity> findByCategory(Category category);

    /**
     * Find all base entities with name containing the given string (case-insensitive).
     * This query is optimized for performance.
     *
     * @param name The name substring to search for
     * @return A list of BaseEntity objects with names containing the given string
     */
    @Query("SELECT be FROM BaseEntity be WHERE LOWER(be.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<BaseEntity> findByNameContainingIgnoreCase(String name);

    /**
     * Find all base entities with their baseEntityLevels eagerly loaded.
     * This query uses a join fetch to load the baseEntityLevels in a single query.
     * It also explicitly selects the category field to ensure it's properly loaded.
     *
     * @return A list of BaseEntity objects with their baseEntityLevels eagerly loaded
     */
    @Query("SELECT DISTINCT be FROM BaseEntity be LEFT JOIN FETCH be.baseEntityLevels")
    List<BaseEntity> findAllWithLevels();

    /**
     * Find a base entity by ID with its baseEntityLevels eagerly loaded.
     * This query uses a join fetch to load the baseEntityLevels in a single query.
     *
     * @param id The ID of the base entity to find
     * @return The BaseEntity object with the given ID, with its baseEntityLevels eagerly loaded
     */
    @Query("SELECT be FROM BaseEntity be LEFT JOIN FETCH be.baseEntityLevels WHERE be.id = :id")
    BaseEntity findByIdWithLevels(Long id);

    /**
     * Find base entity summaries by category.
     * This query uses projection to efficiently retrieve only the necessary data
     * without loading the entire entity and its relationships.
     *
     * @param category The category to filter by
     * @return A list of BaseEntitySummary projections with basic entity information
     */
    List<BaseEntitySummary> findSummaryByCategory(Category category);

    /**
     * Find all base entity summaries.
     * This query uses projection to efficiently retrieve only the necessary data
     * without loading the entire entity and its relationships.
     *
     * @return A list of BaseEntitySummary projections with basic entity information
     */
    List<BaseEntitySummary> findAllProjectedBy();
}
