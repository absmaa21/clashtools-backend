package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.mapper.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Abstract base service class for CRUD operations.
 * This class provides default implementations for common CRUD operations
 * that can be inherited by specific service classes.
 *
 * @param <E> The entity type
 * @param <D> The DTO type
 * @param <R> The request DTO type
 * @param <ID> The ID type
 */
@RequiredArgsConstructor
public abstract class AbstractCrudService<E, D, R, ID> implements CrudService<E, D, R, ID> {

    protected final JpaRepository<E, ID> repository;
    protected final EntityMapper<E, D, R> mapper;

    /**
     * Retrieves all entities from the database.
     * This method fetches all entities of the specified type from the database
     * and converts them to DTOs using the mapper.
     *
     * @return A list of DTOs representing all entities in the database
     */
    @Override
    public List<D> getAll() {
        return mapper.toDTOList(repository.findAll());
    }

    /**
     * Retrieves a specific entity by its unique identifier.
     * This method searches for an entity with the given ID in the database,
     * throws an exception if not found, and converts it to a DTO.
     *
     * @param id The unique identifier of the entity to retrieve
     * @return A DTO representing the requested entity
     * @throws IllegalArgumentException If no entity with the given ID exists in the database
     */
    @Override
    public D getById(ID id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity not found with id: " + id)));
    }

    /**
     * Creates a new entity in the database.
     * This method converts the provided request DTO to an entity object,
     * persists it to the database, and returns the created entity as a DTO.
     *
     * @param request The request DTO containing the data for the new entity
     * @return A DTO representing the newly created entity
     */
    @Override
    public D create(R request) {
        return mapper.toDTO(repository.save(mapper.toEntity(request)));
    }

    /**
     * Updates an existing entity in the database.
     * This method verifies that an entity with the given ID exists,
     * converts the request DTO to an entity, sets its ID,
     * persists the changes to the database, and returns the updated entity as a DTO.
     *
     * @param id The unique identifier of the entity to update
     * @param request The request DTO containing the updated data
     * @return A DTO representing the updated entity
     * @throws IllegalArgumentException If no entity with the given ID exists in the database
     */
    @Override
    public D update(ID id, R request) {
        repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity not found with id: " + id));

        E entity = mapper.toEntity(request);
        setEntityId(entity, id);
        return mapper.toDTO(repository.save(entity));
    }

    /**
     * Deletes an entity from the database.
     * This method removes the entity with the given ID from the database.
     * If no entity with the given ID exists, the operation completes silently.
     *
     * @param id The unique identifier of the entity to delete
     */
    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }

    /**
     * Sets the ID of an entity.
     * Implemented by subclasses to set the ID field of the specific entity type.
     *
     * @param entity Entity to set the ID for
     * @param id ID value to set
     */
    protected abstract void setEntityId(E entity, ID id);
}
