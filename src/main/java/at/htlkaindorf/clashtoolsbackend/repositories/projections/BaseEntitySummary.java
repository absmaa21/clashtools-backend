package at.htlkaindorf.clashtoolsbackend.repositories.projections;

import at.htlkaindorf.clashtoolsbackend.pojos.Category;

/**
 * Projection interface for BaseEntity summary data.
 * This interface defines a subset of BaseEntity properties that can be
 * efficiently retrieved from the database without loading the entire entity.
 * It's useful for list views and search results where only basic information is needed.
 */
public interface BaseEntitySummary {

    /**
     * Gets the ID of the base entity.
     *
     * @return The ID
     */
    Long getId();

    /**
     * Gets the name of the base entity.
     *
     * @return The name
     */
    String getName();

    /**
     * Gets the category of the base entity.
     *
     * @return The category
     */
    Category getCategory();
}
