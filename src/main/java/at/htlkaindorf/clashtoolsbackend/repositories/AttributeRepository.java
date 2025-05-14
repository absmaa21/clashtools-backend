package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.Attribute;
import at.htlkaindorf.clashtoolsbackend.pojos.AttributeName;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Tag(name = "AttributeRepository", description = "Repository for managing Attribute objects")
public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    /**
     * Find all attributes by attribute name
     *
     * @param attributeName the attribute name to search for
     * @return list of attributes with the given attribute name
     */
    List<Attribute> findByAttributeName(AttributeName attributeName);

    /**
     * Find all attributes by attribute name id
     *
     * @param attributeNameId the ID of the attribute name
     * @return list of attributes with the given attribute name ID
     */
    List<Attribute> findByAttributeNameId(Long attributeNameId);
}
