package com.modis.edu.web.rest;

import com.modis.edu.domain.Goal;
import com.modis.edu.repository.GoalRepository;
import com.modis.edu.service.GoalService;
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
 * REST controller for managing {@link com.modis.edu.domain.Goal}.
 */
@RestController
@RequestMapping("/api")
public class GoalResource {

    private final Logger log = LoggerFactory.getLogger(GoalResource.class);

    private static final String ENTITY_NAME = "goal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoalService goalService;

    private final GoalRepository goalRepository;

    public GoalResource(GoalService goalService, GoalRepository goalRepository) {
        this.goalService = goalService;
        this.goalRepository = goalRepository;
    }

    /**
     * {@code POST  /goals} : Create a new goal.
     *
     * @param goal the goal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goal, or with status {@code 400 (Bad Request)} if the goal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/goals")
    public ResponseEntity<Goal> createGoal(@RequestBody Goal goal) throws URISyntaxException {
        log.debug("REST request to save Goal : {}", goal);
        if (goal.getId() != null) {
            throw new BadRequestAlertException("A new goal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Goal result = goalService.save(goal);
        return ResponseEntity
            .created(new URI("/api/goals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /goals/:id} : Updates an existing goal.
     *
     * @param id the id of the goal to save.
     * @param goal the goal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goal,
     * or with status {@code 400 (Bad Request)} if the goal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/goals/{id}")
    public ResponseEntity<Goal> updateGoal(@PathVariable(value = "id", required = false) final String id, @RequestBody Goal goal)
        throws URISyntaxException {
        log.debug("REST request to update Goal : {}, {}", id, goal);
        if (goal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Goal result = goalService.update(goal);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goal.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /goals/:id} : Partial updates given fields of an existing goal, field will ignore if it is null
     *
     * @param id the id of the goal to save.
     * @param goal the goal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goal,
     * or with status {@code 400 (Bad Request)} if the goal is not valid,
     * or with status {@code 404 (Not Found)} if the goal is not found,
     * or with status {@code 500 (Internal Server Error)} if the goal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/goals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Goal> partialUpdateGoal(@PathVariable(value = "id", required = false) final String id, @RequestBody Goal goal)
        throws URISyntaxException {
        log.debug("REST request to partial update Goal partially : {}, {}", id, goal);
        if (goal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Goal> result = goalService.partialUpdate(goal);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goal.getId()));
    }

    /**
     * {@code GET  /goals} : get all the goals.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goals in body.
     */
    @GetMapping("/goals")
    public List<Goal> getAllGoals(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Goals");
        return goalService.findAll();
    }

    /**
     * {@code GET  /goals/:id} : get the "id" goal.
     *
     * @param id the id of the goal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/goals/{id}")
    public ResponseEntity<Goal> getGoal(@PathVariable String id) {
        log.debug("REST request to get Goal : {}", id);
        Optional<Goal> goal = goalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goal);
    }

    /**
     * {@code DELETE  /goals/:id} : delete the "id" goal.
     *
     * @param id the id of the goal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/goals/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable String id) {
        log.debug("REST request to delete Goal : {}", id);
        goalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
