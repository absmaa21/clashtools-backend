package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing Level entities.
 * Provides methods for CRUD operations and custom queries related to levels.
 */
@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {

    /**
     * Find a level by its level value
     *
     * @param level the level value to search for
     * @return the level if found
     */
    Optional<Level> findByLevel(int level);

    /**
     * Check if a level with the given level value exists
     *
     * @param level the level value to check
     * @return true if a level with the level value exists, false otherwise
     */
    boolean existsByLevel(int level);
}
