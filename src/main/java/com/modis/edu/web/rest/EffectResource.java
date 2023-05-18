package com.modis.edu.web.rest;

import com.modis.edu.domain.Effect;
import com.modis.edu.repository.EffectRepository;
import com.modis.edu.service.EffectService;
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
 * REST controller for managing {@link com.modis.edu.domain.Effect}.
 */
@RestController
@RequestMapping("/api")
public class EffectResource {

    private final Logger log = LoggerFactory.getLogger(EffectResource.class);

    private static final String ENTITY_NAME = "effect";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EffectService effectService;

    private final EffectRepository effectRepository;

    public EffectResource(EffectService effectService, EffectRepository effectRepository) {
        this.effectService = effectService;
        this.effectRepository = effectRepository;
    }

    /**
     * {@code POST  /effects} : Create a new effect.
     *
     * @param effect the effect to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new effect, or with status {@code 400 (Bad Request)} if the effect has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/effects")
    public ResponseEntity<Effect> createEffect(@RequestBody Effect effect) throws URISyntaxException {
        log.debug("REST request to save Effect : {}", effect);
        if (effect.getId() != null) {
            throw new BadRequestAlertException("A new effect cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Effect result = effectService.save(effect);
        return ResponseEntity
            .created(new URI("/api/effects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /effects/:id} : Updates an existing effect.
     *
     * @param id the id of the effect to save.
     * @param effect the effect to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated effect,
     * or with status {@code 400 (Bad Request)} if the effect is not valid,
     * or with status {@code 500 (Internal Server Error)} if the effect couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/effects/{id}")
    public ResponseEntity<Effect> updateEffect(@PathVariable(value = "id", required = false) final String id, @RequestBody Effect effect)
        throws URISyntaxException {
        log.debug("REST request to update Effect : {}, {}", id, effect);
        if (effect.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, effect.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!effectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Effect result = effectService.update(effect);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, effect.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /effects/:id} : Partial updates given fields of an existing effect, field will ignore if it is null
     *
     * @param id the id of the effect to save.
     * @param effect the effect to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated effect,
     * or with status {@code 400 (Bad Request)} if the effect is not valid,
     * or with status {@code 404 (Not Found)} if the effect is not found,
     * or with status {@code 500 (Internal Server Error)} if the effect couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/effects/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Effect> partialUpdateEffect(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Effect effect
    ) throws URISyntaxException {
        log.debug("REST request to partial update Effect partially : {}, {}", id, effect);
        if (effect.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, effect.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!effectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Effect> result = effectService.partialUpdate(effect);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, effect.getId()));
    }

    /**
     * {@code GET  /effects} : get all the effects.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of effects in body.
     */
    @GetMapping("/effects")
    public List<Effect> getAllEffects(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Effects");
        return effectService.findAll();
    }

    /**
     * {@code GET  /effects/:id} : get the "id" effect.
     *
     * @param id the id of the effect to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the effect, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/effects/{id}")
    public ResponseEntity<Effect> getEffect(@PathVariable String id) {
        log.debug("REST request to get Effect : {}", id);
        Optional<Effect> effect = effectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(effect);
    }

    /**
     * {@code DELETE  /effects/:id} : delete the "id" effect.
     *
     * @param id the id of the effect to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/effects/{id}")
    public ResponseEntity<Void> deleteEffect(@PathVariable String id) {
        log.debug("REST request to delete Effect : {}", id);
        effectService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
