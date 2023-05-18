package com.modis.edu.service;

import com.modis.edu.domain.Concept;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Concept}.
 */
public interface ConceptService {
    /**
     * Save a concept.
     *
     * @param concept the entity to save.
     * @return the persisted entity.
     */
    Concept save(Concept concept);

    /**
     * Updates a concept.
     *
     * @param concept the entity to update.
     * @return the persisted entity.
     */
    Concept update(Concept concept);

    /**
     * Partially updates a concept.
     *
     * @param concept the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Concept> partialUpdate(Concept concept);

    /**
     * Get all the concepts.
     *
     * @return the list of entities.
     */
    List<Concept> findAll();

    /**
     * Get all the concepts with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Concept> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" concept.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Concept> findOne(String id);

    /**
     * Delete the "id" concept.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
