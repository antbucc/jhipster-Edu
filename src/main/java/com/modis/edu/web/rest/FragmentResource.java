package com.modis.edu.web.rest;

import com.modis.edu.domain.Fragment;
import com.modis.edu.repository.FragmentRepository;
import com.modis.edu.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.modis.edu.domain.Fragment}.
 */
@RestController
@RequestMapping("/api")
public class FragmentResource {

    private final Logger log = LoggerFactory.getLogger(FragmentResource.class);

    private static final String ENTITY_NAME = "fragment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FragmentRepository fragmentRepository;

    public FragmentResource(FragmentRepository fragmentRepository) {
        this.fragmentRepository = fragmentRepository;
    }

    /**
     * {@code POST  /fragments} : Create a new fragment.
     *
     * @param fragment the fragment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fragment, or with status {@code 400 (Bad Request)} if the fragment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fragments")
    public ResponseEntity<Fragment> createFragment(@RequestBody Fragment fragment) throws URISyntaxException {
        log.debug("REST request to save Fragment : {}", fragment);
        if (fragment.getId() != null) {
            throw new BadRequestAlertException("A new fragment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Fragment result = fragmentRepository.save(fragment);
        return ResponseEntity
            .created(new URI("/api/fragments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /fragments/:id} : Updates an existing fragment.
     *
     * @param id the id of the fragment to save.
     * @param fragment the fragment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fragment,
     * or with status {@code 400 (Bad Request)} if the fragment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fragment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fragments/{id}")
    public ResponseEntity<Fragment> updateFragment(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Fragment fragment
    ) throws URISyntaxException {
        log.debug("REST request to update Fragment : {}, {}", id, fragment);
        if (fragment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fragment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fragmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Fragment result = fragmentRepository.save(fragment);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fragment.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /fragments/:id} : Partial updates given fields of an existing fragment, field will ignore if it is null
     *
     * @param id the id of the fragment to save.
     * @param fragment the fragment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fragment,
     * or with status {@code 400 (Bad Request)} if the fragment is not valid,
     * or with status {@code 404 (Not Found)} if the fragment is not found,
     * or with status {@code 500 (Internal Server Error)} if the fragment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fragments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Fragment> partialUpdateFragment(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Fragment fragment
    ) throws URISyntaxException {
        log.debug("REST request to partial update Fragment partially : {}, {}", id, fragment);
        if (fragment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fragment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fragmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Fragment> result = fragmentRepository
            .findById(fragment.getId())
            .map(existingFragment -> {
                if (fragment.getTitle() != null) {
                    existingFragment.setTitle(fragment.getTitle());
                }

                return existingFragment;
            })
            .map(fragmentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fragment.getId())
        );
    }

    /**
     * {@code GET  /fragments} : get all the fragments.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fragments in body.
     */
    @GetMapping("/fragments")
    public List<Fragment> getAllFragments(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Fragments");
        if (eagerload) {
            return fragmentRepository.findAllWithEagerRelationships();
        } else {
            return fragmentRepository.findAll();
        }
    }

    /**
     * {@code GET  /fragments/:id} : get the "id" fragment.
     *
     * @param id the id of the fragment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fragment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fragments/{id}")
    public ResponseEntity<Fragment> getFragment(@PathVariable String id) {
        log.debug("REST request to get Fragment : {}", id);
        Optional<Fragment> fragment = fragmentRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(fragment);
    }

    /**
     * {@code DELETE  /fragments/:id} : delete the "id" fragment.
     *
     * @param id the id of the fragment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fragments/{id}")
    public ResponseEntity<Void> deleteFragment(@PathVariable String id) {
        log.debug("REST request to delete Fragment : {}", id);
        fragmentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
