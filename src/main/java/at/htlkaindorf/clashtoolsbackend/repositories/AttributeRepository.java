package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.Attribute;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Tag(name = "AttributeRepository", description = "Repository for managing Attribute objects")
public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    /**
     * Find all attributes by attribute name id
     *
     * @param attributeNameId the ID of the attribute name
     * @return list of attributes with the given attribute name ID
     */
    List<Attribute> findByAttributeNameId(Long attributeNameId);
}
