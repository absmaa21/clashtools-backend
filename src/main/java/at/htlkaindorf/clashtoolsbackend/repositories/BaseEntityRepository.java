package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Tag(name = "BaseEntityRepository", description = "Repository for managing BaseEntity objects")
public interface BaseEntityRepository extends JpaRepository<BaseEntity, Long> {

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
}
