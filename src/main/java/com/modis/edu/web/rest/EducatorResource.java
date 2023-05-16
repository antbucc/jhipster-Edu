package com.modis.edu.web.rest;

import com.modis.edu.domain.Educator;
import com.modis.edu.repository.EducatorRepository;
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
 * REST controller for managing {@link com.modis.edu.domain.Educator}.
 */
@RestController
@RequestMapping("/api")
public class EducatorResource {

    private final Logger log = LoggerFactory.getLogger(EducatorResource.class);

    private static final String ENTITY_NAME = "educator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EducatorRepository educatorRepository;

    public EducatorResource(EducatorRepository educatorRepository) {
        this.educatorRepository = educatorRepository;
    }

    /**
     * {@code POST  /educators} : Create a new educator.
     *
     * @param educator the educator to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new educator, or with status {@code 400 (Bad Request)} if the educator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/educators")
    public ResponseEntity<Educator> createEducator(@RequestBody Educator educator) throws URISyntaxException {
        log.debug("REST request to save Educator : {}", educator);
        if (educator.getId() != null) {
            throw new BadRequestAlertException("A new educator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Educator result = educatorRepository.save(educator);
        return ResponseEntity
            .created(new URI("/api/educators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /educators/:id} : Updates an existing educator.
     *
     * @param id the id of the educator to save.
     * @param educator the educator to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated educator,
     * or with status {@code 400 (Bad Request)} if the educator is not valid,
     * or with status {@code 500 (Internal Server Error)} if the educator couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/educators/{id}")
    public ResponseEntity<Educator> updateEducator(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Educator educator
    ) throws URISyntaxException {
        log.debug("REST request to update Educator : {}, {}", id, educator);
        if (educator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, educator.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!educatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Educator result = educatorRepository.save(educator);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, educator.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /educators/:id} : Partial updates given fields of an existing educator, field will ignore if it is null
     *
     * @param id the id of the educator to save.
     * @param educator the educator to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated educator,
     * or with status {@code 400 (Bad Request)} if the educator is not valid,
     * or with status {@code 404 (Not Found)} if the educator is not found,
     * or with status {@code 500 (Internal Server Error)} if the educator couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/educators/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Educator> partialUpdateEducator(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Educator educator
    ) throws URISyntaxException {
        log.debug("REST request to partial update Educator partially : {}, {}", id, educator);
        if (educator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, educator.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!educatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Educator> result = educatorRepository
            .findById(educator.getId())
            .map(existingEducator -> {
                if (educator.getFirstName() != null) {
                    existingEducator.setFirstName(educator.getFirstName());
                }
                if (educator.getLastName() != null) {
                    existingEducator.setLastName(educator.getLastName());
                }
                if (educator.getEmail() != null) {
                    existingEducator.setEmail(educator.getEmail());
                }

                return existingEducator;
            })
            .map(educatorRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, educator.getId())
        );
    }

    /**
     * {@code GET  /educators} : get all the educators.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of educators in body.
     */
    @GetMapping("/educators")
    public List<Educator> getAllEducators(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Educators");
        if (eagerload) {
            return educatorRepository.findAllWithEagerRelationships();
        } else {
            return educatorRepository.findAll();
        }
    }

    /**
     * {@code GET  /educators/:id} : get the "id" educator.
     *
     * @param id the id of the educator to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the educator, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/educators/{id}")
    public ResponseEntity<Educator> getEducator(@PathVariable String id) {
        log.debug("REST request to get Educator : {}", id);
        Optional<Educator> educator = educatorRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(educator);
    }

    /**
     * {@code DELETE  /educators/:id} : delete the "id" educator.
     *
     * @param id the id of the educator to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/educators/{id}")
    public ResponseEntity<Void> deleteEducator(@PathVariable String id) {
        log.debug("REST request to delete Educator : {}", id);
        educatorRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
