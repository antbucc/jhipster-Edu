package com.modis.edu.service;

import com.modis.edu.domain.Learner;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Learner}.
 */
public interface LearnerService {
    /**
     * Save a learner.
     *
     * @param learner the entity to save.
     * @return the persisted entity.
     */
    Learner save(Learner learner);

    /**
     * Updates a learner.
     *
     * @param learner the entity to update.
     * @return the persisted entity.
     */
    Learner update(Learner learner);

    /**
     * Partially updates a learner.
     *
     * @param learner the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Learner> partialUpdate(Learner learner);

    /**
     * Get all the learners.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Learner> findAll(Pageable pageable);

    /**
     * Get the "id" learner.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Learner> findOne(String id);

    /**
     * Delete the "id" learner.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
