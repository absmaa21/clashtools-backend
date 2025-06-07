package at.htlkaindorf.clashtoolsbackend.factory;

import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * Factory for creating BaseEntity objects.
 * This factory encapsulates the complex creation logic for BaseEntity objects,
 * making it easier to create them with consistent default values.
 */
@Component
public class BaseEntityFactory {

    /**
     * Creates a new BaseEntity with the given name and category.
     *
     * @param name The name of the base entity
     * @param category The category of the base entity
     * @return A new BaseEntity instance
     */
    public BaseEntity createBaseEntity(String name, Category category) {
        return BaseEntity.builder()
                .name(name)
                .category(category)
                .baseEntityLevels(new HashSet<>())
                .build();
    }

    /**
     * Creates a new BaseEntity with the given name, category, and a predefined set of levels.
     * This method can be used when creating a base entity that should have default levels.
     *
     * @param name The name of the base entity
     * @param category The category of the base entity
     * @param defaultLevels Whether to create default levels for this entity
     * @return A new BaseEntity instance
     */
    public BaseEntity createBaseEntity(String name, Category category, boolean defaultLevels) {
        BaseEntity baseEntity = createBaseEntity(name, category);

        if (defaultLevels) {
            // Here we could add logic to create default levels based on the category
            // For example, buildings might have levels 1-15, while troops might have levels 1-9
            // This would be implemented based on specific business requirements
        }

        return baseEntity;
    }
}
