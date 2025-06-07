package at.htlkaindorf.clashtoolsbackend.mapper;

import java.util.List;

/**
 * Generic base mapper interface for converting between entities and DTOs.
 * This interface defines common mapping methods that can be implemented by specific mappers.
 *
 * @param <E> The entity type
 * @param <D> The DTO type
 * @param <R> The request DTO type
 */
public interface EntityMapper<E, D, R> {

    /**
     * Converts an entity to a DTO.
     *
     * @param entity The entity to convert
     * @return The converted DTO
     */
    D toDTO(E entity);

    /**
     * Converts a request DTO to an entity.
     *
     * @param requestDTO The request DTO to convert
     * @return The converted entity
     */
    E toEntity(R requestDTO);

    /**
     * Converts a list of entities to a list of DTOs.
     *
     * @param entities The list of entities to convert
     * @return The list of converted DTOs
     */
    List<D> toDTOList(List<E> entities);
}
