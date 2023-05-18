package com.modis.edu.service;

import com.modis.edu.domain.Effect;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Effect}.
 */
public interface EffectService {
    /**
     * Save a effect.
     *
     * @param effect the entity to save.
     * @return the persisted entity.
     */
    Effect save(Effect effect);

    /**
     * Updates a effect.
     *
     * @param effect the entity to update.
     * @return the persisted entity.
     */
    Effect update(Effect effect);

    /**
     * Partially updates a effect.
     *
     * @param effect the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Effect> partialUpdate(Effect effect);

    /**
     * Get all the effects.
     *
     * @return the list of entities.
     */
    List<Effect> findAll();

    /**
     * Get all the effects with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Effect> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" effect.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Effect> findOne(String id);

    /**
     * Delete the "id" effect.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
