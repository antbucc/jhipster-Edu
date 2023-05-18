package com.modis.edu.web.rest;

import com.modis.edu.domain.Learner;
import com.modis.edu.repository.LearnerRepository;
import com.modis.edu.service.LearnerService;
import com.modis.edu.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.modis.edu.domain.Learner}.
 */
@RestController
@RequestMapping("/api")
public class LearnerResource {

    private final Logger log = LoggerFactory.getLogger(LearnerResource.class);

    private static final String ENTITY_NAME = "learner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LearnerService learnerService;

    private final LearnerRepository learnerRepository;

    public LearnerResource(LearnerService learnerService, LearnerRepository learnerRepository) {
        this.learnerService = learnerService;
        this.learnerRepository = learnerRepository;
    }

    /**
     * {@code POST  /learners} : Create a new learner.
     *
     * @param learner the learner to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new learner, or with status {@code 400 (Bad Request)} if the learner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/learners")
    public ResponseEntity<Learner> createLearner(@RequestBody Learner learner) throws URISyntaxException {
        log.debug("REST request to save Learner : {}", learner);
        if (learner.getId() != null) {
            throw new BadRequestAlertException("A new learner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Learner result = learnerService.save(learner);
        return ResponseEntity
            .created(new URI("/api/learners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /learners/:id} : Updates an existing learner.
     *
     * @param id the id of the learner to save.
     * @param learner the learner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated learner,
     * or with status {@code 400 (Bad Request)} if the learner is not valid,
     * or with status {@code 500 (Internal Server Error)} if the learner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/learners/{id}")
    public ResponseEntity<Learner> updateLearner(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Learner learner
    ) throws URISyntaxException {
        log.debug("REST request to update Learner : {}, {}", id, learner);
        if (learner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, learner.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!learnerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Learner result = learnerService.update(learner);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, learner.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /learners/:id} : Partial updates given fields of an existing learner, field will ignore if it is null
     *
     * @param id the id of the learner to save.
     * @param learner the learner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated learner,
     * or with status {@code 400 (Bad Request)} if the learner is not valid,
     * or with status {@code 404 (Not Found)} if the learner is not found,
     * or with status {@code 500 (Internal Server Error)} if the learner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/learners/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Learner> partialUpdateLearner(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Learner learner
    ) throws URISyntaxException {
        log.debug("REST request to partial update Learner partially : {}, {}", id, learner);
        if (learner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, learner.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!learnerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Learner> result = learnerService.partialUpdate(learner);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, learner.getId()));
    }

    /**
     * {@code GET  /learners} : get all the learners.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of learners in body.
     */
    @GetMapping("/learners")
    public ResponseEntity<List<Learner>> getAllLearners(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Learners");
        Page<Learner> page = learnerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /learners/:id} : get the "id" learner.
     *
     * @param id the id of the learner to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the learner, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/learners/{id}")
    public ResponseEntity<Learner> getLearner(@PathVariable String id) {
        log.debug("REST request to get Learner : {}", id);
        Optional<Learner> learner = learnerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(learner);
    }

    /**
     * {@code DELETE  /learners/:id} : delete the "id" learner.
     *
     * @param id the id of the learner to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/learners/{id}")
    public ResponseEntity<Void> deleteLearner(@PathVariable String id) {
        log.debug("REST request to delete Learner : {}", id);
        learnerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
