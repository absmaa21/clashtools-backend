package at.htlkaindorf.clashtoolsbackend.service;

import java.util.List;

/**
 * Generic base service interface for CRUD operations.
 * This interface defines common methods for Create, Read, Update, Delete operations
 * that can be implemented by specific services.
 *
 * @param <E> The entity type
 * @param <D> The DTO type
 * @param <R> The request DTO type
 * @param <ID> The ID type
 */
public interface CrudService<E, D, R, ID> {

    /**
     * Retrieves all entities.
     *
     * @return A list of DTOs representing all entities
     */
    List<D> getAll();

    /**
     * Retrieves a specific entity by its unique identifier.
     *
     * @param id The unique identifier of the entity to retrieve
     * @return A DTO representing the requested entity
     * @throws IllegalArgumentException If no entity with the given ID exists
     */
    D getById(ID id);

    /**
     * Creates a new entity.
     *
     * @param request The request DTO containing the data for the new entity
     * @return A DTO representing the newly created entity
     */
    D create(R request);

    /**
     * Updates an existing entity.
     *
     * @param id The unique identifier of the entity to update
     * @param request The request DTO containing the updated data
     * @return A DTO representing the updated entity
     * @throws IllegalArgumentException If no entity with the given ID exists
     */
    D update(ID id, R request);

    /**
     * Deletes an entity.
     *
     * @param id The unique identifier of the entity to delete
     */
    void delete(ID id);
}
