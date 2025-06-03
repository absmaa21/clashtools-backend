package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.AttributeName;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Category entities.
 * Provides methods for CRUD operations and custom queries related to categories.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Find a category by its name
     *
     * @param name the name to search for
     * @return the category if found
     */
    Optional<Category> findByName(String name);

    /**
     * Check if a category with the given name exists
     *
     * @param name the name to check
     * @return true if a category with the name exists, false otherwise
     */
    boolean existsByName(String name);

    /**
     * Find all categories that contain a specific attribute name
     *
     * @param attributeName the attribute name to search for
     * @return a list of categories
     */
    List<Category> findByAttributeNamesContaining(AttributeName attributeName);

    /**
     * Find all categories that contain a specific base entity
     *
     * @param baseEntity the base entity to search for
     * @return a list of categories
     */
    List<Category> findByBaseEntitiesContaining(BaseEntity baseEntity);
}
