package com.modis.edu.service;

import com.modis.edu.domain.Condition;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Condition}.
 */
public interface ConditionService {
    /**
     * Save a condition.
     *
     * @param condition the entity to save.
     * @return the persisted entity.
     */
    Condition save(Condition condition);

    /**
     * Updates a condition.
     *
     * @param condition the entity to update.
     * @return the persisted entity.
     */
    Condition update(Condition condition);

    /**
     * Partially updates a condition.
     *
     * @param condition the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Condition> partialUpdate(Condition condition);

    /**
     * Get all the conditions.
     *
     * @return the list of entities.
     */
    List<Condition> findAll();

    /**
     * Get the "id" condition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Condition> findOne(String id);

    /**
     * Delete the "id" condition.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
