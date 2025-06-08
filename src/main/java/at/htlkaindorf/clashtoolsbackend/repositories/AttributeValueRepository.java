package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.Attribute;
import at.htlkaindorf.clashtoolsbackend.pojos.AttributeValue;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntityLevel;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing AttributeValue entities.
 * Provides methods for CRUD operations and custom queries related to attribute values.
 */
@Repository
@Tag(name = "AttributeValueRepository", description = "Repository for managing AttributeValue objects")
public interface AttributeValueRepository extends JpaRepository<AttributeValue<?>, Long> {

    /**
     * Find all attribute values associated with a specific attribute ID
     *
     * @param attributeId the ID of the attribute to search for
     * @return a list of attribute values
     */
    List<AttributeValue<?>> findByAttributeId(Long attributeId);


    /**
     * Find all attribute values associated with a specific base entity level ID
     *
     * @param baseEntityLevelId the ID of the base entity level to search for
     * @return a list of attribute values
     */
    List<AttributeValue<?>> findByBaseEntityLevelId(Long baseEntityLevelId);

    /**
     * Delete all attribute values associated with a specific attribute
     *
     * @param attribute the attribute whose values should be deleted
     */
    void deleteByAttribute(Attribute attribute);

    /**
     * Delete all attribute values associated with a specific base entity level
     *
     * @param baseEntityLevel the base entity level whose values should be deleted
     */
    void deleteByBaseEntityLevel(BaseEntityLevel baseEntityLevel);
}
