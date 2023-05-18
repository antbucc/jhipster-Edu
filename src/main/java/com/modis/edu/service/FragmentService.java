package com.modis.edu.service;

import com.modis.edu.domain.Fragment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Fragment}.
 */
public interface FragmentService {
    /**
     * Save a fragment.
     *
     * @param fragment the entity to save.
     * @return the persisted entity.
     */
    Fragment save(Fragment fragment);

    /**
     * Updates a fragment.
     *
     * @param fragment the entity to update.
     * @return the persisted entity.
     */
    Fragment update(Fragment fragment);

    /**
     * Partially updates a fragment.
     *
     * @param fragment the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Fragment> partialUpdate(Fragment fragment);

    /**
     * Get all the fragments.
     *
     * @return the list of entities.
     */
    List<Fragment> findAll();

    /**
     * Get all the fragments with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Fragment> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" fragment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Fragment> findOne(String id);

    /**
     * Delete the "id" fragment.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
