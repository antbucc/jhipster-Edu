package com.modis.edu.service;

import com.modis.edu.domain.Module;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Module}.
 */
public interface ModuleService {
    /**
     * Save a module.
     *
     * @param module the entity to save.
     * @return the persisted entity.
     */
    Module save(Module module);

    /**
     * Updates a module.
     *
     * @param module the entity to update.
     * @return the persisted entity.
     */
    Module update(Module module);

    /**
     * Partially updates a module.
     *
     * @param module the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Module> partialUpdate(Module module);

    /**
     * Get all the modules.
     *
     * @return the list of entities.
     */
    List<Module> findAll();

    /**
     * Get all the modules with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Module> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" module.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Module> findOne(String id);

    /**
     * Delete the "id" module.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
