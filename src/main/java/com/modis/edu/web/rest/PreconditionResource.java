package com.modis.edu.web.rest;

import com.modis.edu.domain.Precondition;
import com.modis.edu.repository.PreconditionRepository;
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
 * REST controller for managing {@link com.modis.edu.domain.Precondition}.
 */
@RestController
@RequestMapping("/api")
public class PreconditionResource {

    private final Logger log = LoggerFactory.getLogger(PreconditionResource.class);

    private static final String ENTITY_NAME = "precondition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreconditionRepository preconditionRepository;

    public PreconditionResource(PreconditionRepository preconditionRepository) {
        this.preconditionRepository = preconditionRepository;
    }

    /**
     * {@code POST  /preconditions} : Create a new precondition.
     *
     * @param precondition the precondition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new precondition, or with status {@code 400 (Bad Request)} if the precondition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/preconditions")
    public ResponseEntity<Precondition> createPrecondition(@RequestBody Precondition precondition) throws URISyntaxException {
        log.debug("REST request to save Precondition : {}", precondition);
        if (precondition.getId() != null) {
            throw new BadRequestAlertException("A new precondition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Precondition result = preconditionRepository.save(precondition);
        return ResponseEntity
            .created(new URI("/api/preconditions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /preconditions/:id} : Updates an existing precondition.
     *
     * @param id the id of the precondition to save.
     * @param precondition the precondition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated precondition,
     * or with status {@code 400 (Bad Request)} if the precondition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the precondition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/preconditions/{id}")
    public ResponseEntity<Precondition> updatePrecondition(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Precondition precondition
    ) throws URISyntaxException {
        log.debug("REST request to update Precondition : {}, {}", id, precondition);
        if (precondition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, precondition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preconditionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Precondition result = preconditionRepository.save(precondition);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, precondition.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /preconditions/:id} : Partial updates given fields of an existing precondition, field will ignore if it is null
     *
     * @param id the id of the precondition to save.
     * @param precondition the precondition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated precondition,
     * or with status {@code 400 (Bad Request)} if the precondition is not valid,
     * or with status {@code 404 (Not Found)} if the precondition is not found,
     * or with status {@code 500 (Internal Server Error)} if the precondition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/preconditions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Precondition> partialUpdatePrecondition(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Precondition precondition
    ) throws URISyntaxException {
        log.debug("REST request to partial update Precondition partially : {}, {}", id, precondition);
        if (precondition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, precondition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preconditionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Precondition> result = preconditionRepository
            .findById(precondition.getId())
            .map(existingPrecondition -> {
                if (precondition.getTitle() != null) {
                    existingPrecondition.setTitle(precondition.getTitle());
                }

                return existingPrecondition;
            })
            .map(preconditionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, precondition.getId())
        );
    }

    /**
     * {@code GET  /preconditions} : get all the preconditions.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preconditions in body.
     */
    @GetMapping("/preconditions")
    public List<Precondition> getAllPreconditions(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Preconditions");
        if (eagerload) {
            return preconditionRepository.findAllWithEagerRelationships();
        } else {
            return preconditionRepository.findAll();
        }
    }

    /**
     * {@code GET  /preconditions/:id} : get the "id" precondition.
     *
     * @param id the id of the precondition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the precondition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/preconditions/{id}")
    public ResponseEntity<Precondition> getPrecondition(@PathVariable String id) {
        log.debug("REST request to get Precondition : {}", id);
        Optional<Precondition> precondition = preconditionRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(precondition);
    }

    /**
     * {@code DELETE  /preconditions/:id} : delete the "id" precondition.
     *
     * @param id the id of the precondition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/preconditions/{id}")
    public ResponseEntity<Void> deletePrecondition(@PathVariable String id) {
        log.debug("REST request to delete Precondition : {}", id);
        preconditionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
