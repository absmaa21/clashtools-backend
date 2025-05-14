package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.AttributeName;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Tag(name = "AttributeNameRepository", description = "Repository for managing AttributeName objects")
public interface AttributeNameRepository extends JpaRepository<AttributeName, Long> {
    /**
     * Find an attribute name by its name
     *
     * @param name the name to search for
     * @return the attribute name if found, or empty optional otherwise
     */
    Optional<AttributeName> findByName(String name);

    /**
     * Check if an attribute name with the given name exists
     *
     * @param name the name to check
     * @return true if an attribute name with the name exists, false otherwise
     */
    boolean existsByName(String name);
}
