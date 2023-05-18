package com.modis.edu.web.rest;

import com.modis.edu.domain.Concept;
import com.modis.edu.repository.ConceptRepository;
import com.modis.edu.service.ConceptService;
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
 * REST controller for managing {@link com.modis.edu.domain.Concept}.
 */
@RestController
@RequestMapping("/api")
public class ConceptResource {

    private final Logger log = LoggerFactory.getLogger(ConceptResource.class);

    private static final String ENTITY_NAME = "concept";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptService conceptService;

    private final ConceptRepository conceptRepository;

    public ConceptResource(ConceptService conceptService, ConceptRepository conceptRepository) {
        this.conceptService = conceptService;
        this.conceptRepository = conceptRepository;
    }

    /**
     * {@code POST  /concepts} : Create a new concept.
     *
     * @param concept the concept to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new concept, or with status {@code 400 (Bad Request)} if the concept has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concepts")
    public ResponseEntity<Concept> createConcept(@RequestBody Concept concept) throws URISyntaxException {
        log.debug("REST request to save Concept : {}", concept);
        if (concept.getId() != null) {
            throw new BadRequestAlertException("A new concept cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Concept result = conceptService.save(concept);
        return ResponseEntity
            .created(new URI("/api/concepts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /concepts/:id} : Updates an existing concept.
     *
     * @param id the id of the concept to save.
     * @param concept the concept to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concept,
     * or with status {@code 400 (Bad Request)} if the concept is not valid,
     * or with status {@code 500 (Internal Server Error)} if the concept couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concepts/{id}")
    public ResponseEntity<Concept> updateConcept(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Concept concept
    ) throws URISyntaxException {
        log.debug("REST request to update Concept : {}, {}", id, concept);
        if (concept.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, concept.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conceptRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Concept result = conceptService.update(concept);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, concept.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /concepts/:id} : Partial updates given fields of an existing concept, field will ignore if it is null
     *
     * @param id the id of the concept to save.
     * @param concept the concept to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concept,
     * or with status {@code 400 (Bad Request)} if the concept is not valid,
     * or with status {@code 404 (Not Found)} if the concept is not found,
     * or with status {@code 500 (Internal Server Error)} if the concept couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/concepts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Concept> partialUpdateConcept(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Concept concept
    ) throws URISyntaxException {
        log.debug("REST request to partial update Concept partially : {}, {}", id, concept);
        if (concept.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, concept.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conceptRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Concept> result = conceptService.partialUpdate(concept);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, concept.getId()));
    }

    /**
     * {@code GET  /concepts} : get all the concepts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of concepts in body.
     */
    @GetMapping("/concepts")
    public List<Concept> getAllConcepts() {
        log.debug("REST request to get all Concepts");
        return conceptService.findAll();
    }

    /**
     * {@code GET  /concepts/:id} : get the "id" concept.
     *
     * @param id the id of the concept to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the concept, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concepts/{id}")
    public ResponseEntity<Concept> getConcept(@PathVariable String id) {
        log.debug("REST request to get Concept : {}", id);
        Optional<Concept> concept = conceptService.findOne(id);
        return ResponseUtil.wrapOrNotFound(concept);
    }

    /**
     * {@code DELETE  /concepts/:id} : delete the "id" concept.
     *
     * @param id the id of the concept to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concepts/{id}")
    public ResponseEntity<Void> deleteConcept(@PathVariable String id) {
        log.debug("REST request to delete Concept : {}", id);
        conceptService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
