package com.modis.edu.web.rest;

import com.modis.edu.domain.Scenario;
import com.modis.edu.repository.ScenarioRepository;
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
 * REST controller for managing {@link com.modis.edu.domain.Scenario}.
 */
@RestController
@RequestMapping("/api")
public class ScenarioResource {

    private final Logger log = LoggerFactory.getLogger(ScenarioResource.class);

    private static final String ENTITY_NAME = "scenario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScenarioRepository scenarioRepository;

    public ScenarioResource(ScenarioRepository scenarioRepository) {
        this.scenarioRepository = scenarioRepository;
    }

    /**
     * {@code POST  /scenarios} : Create a new scenario.
     *
     * @param scenario the scenario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scenario, or with status {@code 400 (Bad Request)} if the scenario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scenarios")
    public ResponseEntity<Scenario> createScenario(@RequestBody Scenario scenario) throws URISyntaxException {
        log.debug("REST request to save Scenario : {}", scenario);
        if (scenario.getId() != null) {
            throw new BadRequestAlertException("A new scenario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Scenario result = scenarioRepository.save(scenario);
        return ResponseEntity
            .created(new URI("/api/scenarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /scenarios/:id} : Updates an existing scenario.
     *
     * @param id the id of the scenario to save.
     * @param scenario the scenario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scenario,
     * or with status {@code 400 (Bad Request)} if the scenario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scenario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scenarios/{id}")
    public ResponseEntity<Scenario> updateScenario(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Scenario scenario
    ) throws URISyntaxException {
        log.debug("REST request to update Scenario : {}, {}", id, scenario);
        if (scenario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scenario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scenarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Scenario result = scenarioRepository.save(scenario);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scenario.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /scenarios/:id} : Partial updates given fields of an existing scenario, field will ignore if it is null
     *
     * @param id the id of the scenario to save.
     * @param scenario the scenario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scenario,
     * or with status {@code 400 (Bad Request)} if the scenario is not valid,
     * or with status {@code 404 (Not Found)} if the scenario is not found,
     * or with status {@code 500 (Internal Server Error)} if the scenario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/scenarios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Scenario> partialUpdateScenario(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Scenario scenario
    ) throws URISyntaxException {
        log.debug("REST request to partial update Scenario partially : {}, {}", id, scenario);
        if (scenario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scenario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scenarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Scenario> result = scenarioRepository
            .findById(scenario.getId())
            .map(existingScenario -> {
                if (scenario.getTitle() != null) {
                    existingScenario.setTitle(scenario.getTitle());
                }
                if (scenario.getDescription() != null) {
                    existingScenario.setDescription(scenario.getDescription());
                }
                if (scenario.getLanguage() != null) {
                    existingScenario.setLanguage(scenario.getLanguage());
                }

                return existingScenario;
            })
            .map(scenarioRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scenario.getId())
        );
    }

    /**
     * {@code GET  /scenarios} : get all the scenarios.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scenarios in body.
     */
    @GetMapping("/scenarios")
    public ResponseEntity<List<Scenario>> getAllScenarios(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Scenarios");
        Page<Scenario> page;
        if (eagerload) {
            page = scenarioRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = scenarioRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /scenarios/:id} : get the "id" scenario.
     *
     * @param id the id of the scenario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scenario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scenarios/{id}")
    public ResponseEntity<Scenario> getScenario(@PathVariable String id) {
        log.debug("REST request to get Scenario : {}", id);
        Optional<Scenario> scenario = scenarioRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(scenario);
    }

    /**
     * {@code DELETE  /scenarios/:id} : delete the "id" scenario.
     *
     * @param id the id of the scenario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scenarios/{id}")
    public ResponseEntity<Void> deleteScenario(@PathVariable String id) {
        log.debug("REST request to delete Scenario : {}", id);
        scenarioRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
