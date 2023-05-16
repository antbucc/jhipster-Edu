package com.modis.edu.web.rest;

import com.modis.edu.domain.Module;
import com.modis.edu.repository.ModuleRepository;
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
 * REST controller for managing {@link com.modis.edu.domain.Module}.
 */
@RestController
@RequestMapping("/api")
public class ModuleResource {

    private final Logger log = LoggerFactory.getLogger(ModuleResource.class);

    private static final String ENTITY_NAME = "module";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModuleRepository moduleRepository;

    public ModuleResource(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    /**
     * {@code POST  /modules} : Create a new module.
     *
     * @param module the module to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new module, or with status {@code 400 (Bad Request)} if the module has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/modules")
    public ResponseEntity<Module> createModule(@RequestBody Module module) throws URISyntaxException {
        log.debug("REST request to save Module : {}", module);
        if (module.getId() != null) {
            throw new BadRequestAlertException("A new module cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Module result = moduleRepository.save(module);
        return ResponseEntity
            .created(new URI("/api/modules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /modules/:id} : Updates an existing module.
     *
     * @param id the id of the module to save.
     * @param module the module to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated module,
     * or with status {@code 400 (Bad Request)} if the module is not valid,
     * or with status {@code 500 (Internal Server Error)} if the module couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/modules/{id}")
    public ResponseEntity<Module> updateModule(@PathVariable(value = "id", required = false) final String id, @RequestBody Module module)
        throws URISyntaxException {
        log.debug("REST request to update Module : {}, {}", id, module);
        if (module.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, module.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moduleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Module result = moduleRepository.save(module);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, module.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /modules/:id} : Partial updates given fields of an existing module, field will ignore if it is null
     *
     * @param id the id of the module to save.
     * @param module the module to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated module,
     * or with status {@code 400 (Bad Request)} if the module is not valid,
     * or with status {@code 404 (Not Found)} if the module is not found,
     * or with status {@code 500 (Internal Server Error)} if the module couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/modules/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Module> partialUpdateModule(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Module module
    ) throws URISyntaxException {
        log.debug("REST request to partial update Module partially : {}, {}", id, module);
        if (module.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, module.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moduleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Module> result = moduleRepository
            .findById(module.getId())
            .map(existingModule -> {
                if (module.getTitle() != null) {
                    existingModule.setTitle(module.getTitle());
                }
                if (module.getDescription() != null) {
                    existingModule.setDescription(module.getDescription());
                }
                if (module.getStartDate() != null) {
                    existingModule.setStartDate(module.getStartDate());
                }
                if (module.getEndData() != null) {
                    existingModule.setEndData(module.getEndData());
                }
                if (module.getLevel() != null) {
                    existingModule.setLevel(module.getLevel());
                }

                return existingModule;
            })
            .map(moduleRepository::save);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, module.getId()));
    }

    /**
     * {@code GET  /modules} : get all the modules.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modules in body.
     */
    @GetMapping("/modules")
    public List<Module> getAllModules() {
        log.debug("REST request to get all Modules");
        return moduleRepository.findAll();
    }

    /**
     * {@code GET  /modules/:id} : get the "id" module.
     *
     * @param id the id of the module to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the module, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/modules/{id}")
    public ResponseEntity<Module> getModule(@PathVariable String id) {
        log.debug("REST request to get Module : {}", id);
        Optional<Module> module = moduleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(module);
    }

    /**
     * {@code DELETE  /modules/:id} : delete the "id" module.
     *
     * @param id the id of the module to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/modules/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable String id) {
        log.debug("REST request to delete Module : {}", id);
        moduleRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
