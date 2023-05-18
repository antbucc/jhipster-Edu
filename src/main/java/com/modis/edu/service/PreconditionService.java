package com.modis.edu.service;

import com.modis.edu.domain.Precondition;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Precondition}.
 */
public interface PreconditionService {
    /**
     * Save a precondition.
     *
     * @param precondition the entity to save.
     * @return the persisted entity.
     */
    Precondition save(Precondition precondition);

    /**
     * Updates a precondition.
     *
     * @param precondition the entity to update.
     * @return the persisted entity.
     */
    Precondition update(Precondition precondition);

    /**
     * Partially updates a precondition.
     *
     * @param precondition the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Precondition> partialUpdate(Precondition precondition);

    /**
     * Get all the preconditions.
     *
     * @return the list of entities.
     */
    List<Precondition> findAll();

    /**
     * Get all the preconditions with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Precondition> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" precondition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Precondition> findOne(String id);

    /**
     * Delete the "id" precondition.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
