package com.modis.edu.service;

import com.modis.edu.domain.Competence;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Competence}.
 */
public interface CompetenceService {
    /**
     * Save a competence.
     *
     * @param competence the entity to save.
     * @return the persisted entity.
     */
    Competence save(Competence competence);

    /**
     * Updates a competence.
     *
     * @param competence the entity to update.
     * @return the persisted entity.
     */
    Competence update(Competence competence);

    /**
     * Partially updates a competence.
     *
     * @param competence the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Competence> partialUpdate(Competence competence);

    /**
     * Get all the competences.
     *
     * @return the list of entities.
     */
    List<Competence> findAll();

    /**
     * Get all the competences with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Competence> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" competence.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Competence> findOne(String id);

    /**
     * Delete the "id" competence.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
