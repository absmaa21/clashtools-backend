package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.Attribute;
import at.htlkaindorf.clashtoolsbackend.pojos.AttributeValue;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntityLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing AttributeValue entities.
 * Provides methods for CRUD operations and custom queries related to attribute values.
 */
@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue<?>, Long> {

    /**
     * Find all attribute values associated with a specific attribute
     *
     * @param attribute the attribute to search for
     * @return a list of attribute values
     */
    List<AttributeValue<?>> findByAttribute(Attribute attribute);

    /**
     * Find all attribute values associated with a specific attribute ID
     *
     * @param attributeId the ID of the attribute to search for
     * @return a list of attribute values
     */
    List<AttributeValue<?>> findByAttributeId(Long attributeId);

    /**
     * Find all attribute values associated with a specific base entity level
     *
     * @param baseEntityLevel the base entity level to search for
     * @return a list of attribute values
     */
    List<AttributeValue<?>> findByBaseEntityLevel(BaseEntityLevel baseEntityLevel);

    /**
     * Find all attribute values associated with a specific base entity level ID
     *
     * @param baseEntityLevelId the ID of the base entity level to search for
     * @return a list of attribute values
     */
    List<AttributeValue<?>> findByBaseEntityLevelId(Long baseEntityLevelId);

    /**
     * Find an attribute value by its attribute and base entity level
     *
     * @param attribute the attribute to search for
     * @param baseEntityLevel the base entity level to search for
     * @return the attribute value if found
     */
    AttributeValue<?> findByAttributeAndBaseEntityLevel(Attribute attribute, BaseEntityLevel baseEntityLevel);

    /**
     * Find an attribute value by its attribute ID and base entity level ID
     *
     * @param attributeId the ID of the attribute to search for
     * @param baseEntityLevelId the ID of the base entity level to search for
     * @return the attribute value if found
     */
    AttributeValue<?> findByAttributeIdAndBaseEntityLevelId(Long attributeId, Long baseEntityLevelId);

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
