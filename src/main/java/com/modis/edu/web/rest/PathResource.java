package com.modis.edu.web.rest;

import com.modis.edu.domain.Path;
import com.modis.edu.repository.PathRepository;
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
 * REST controller for managing {@link com.modis.edu.domain.Path}.
 */
@RestController
@RequestMapping("/api")
public class PathResource {

    private final Logger log = LoggerFactory.getLogger(PathResource.class);

    private static final String ENTITY_NAME = "path";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PathRepository pathRepository;

    public PathResource(PathRepository pathRepository) {
        this.pathRepository = pathRepository;
    }

    /**
     * {@code POST  /paths} : Create a new path.
     *
     * @param path the path to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new path, or with status {@code 400 (Bad Request)} if the path has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paths")
    public ResponseEntity<Path> createPath(@RequestBody Path path) throws URISyntaxException {
        log.debug("REST request to save Path : {}", path);
        if (path.getId() != null) {
            throw new BadRequestAlertException("A new path cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Path result = pathRepository.save(path);
        return ResponseEntity
            .created(new URI("/api/paths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /paths/:id} : Updates an existing path.
     *
     * @param id the id of the path to save.
     * @param path the path to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated path,
     * or with status {@code 400 (Bad Request)} if the path is not valid,
     * or with status {@code 500 (Internal Server Error)} if the path couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paths/{id}")
    public ResponseEntity<Path> updatePath(@PathVariable(value = "id", required = false) final String id, @RequestBody Path path)
        throws URISyntaxException {
        log.debug("REST request to update Path : {}, {}", id, path);
        if (path.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, path.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pathRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Path result = pathRepository.save(path);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, path.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /paths/:id} : Partial updates given fields of an existing path, field will ignore if it is null
     *
     * @param id the id of the path to save.
     * @param path the path to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated path,
     * or with status {@code 400 (Bad Request)} if the path is not valid,
     * or with status {@code 404 (Not Found)} if the path is not found,
     * or with status {@code 500 (Internal Server Error)} if the path couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/paths/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Path> partialUpdatePath(@PathVariable(value = "id", required = false) final String id, @RequestBody Path path)
        throws URISyntaxException {
        log.debug("REST request to partial update Path partially : {}, {}", id, path);
        if (path.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, path.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pathRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Path> result = pathRepository
            .findById(path.getId())
            .map(existingPath -> {
                if (path.getTitle() != null) {
                    existingPath.setTitle(path.getTitle());
                }
                if (path.getType() != null) {
                    existingPath.setType(path.getType());
                }

                return existingPath;
            })
            .map(pathRepository::save);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, path.getId()));
    }

    /**
     * {@code GET  /paths} : get all the paths.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paths in body.
     */
    @GetMapping("/paths")
    public List<Path> getAllPaths() {
        log.debug("REST request to get all Paths");
        return pathRepository.findAll();
    }

    /**
     * {@code GET  /paths/:id} : get the "id" path.
     *
     * @param id the id of the path to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the path, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paths/{id}")
    public ResponseEntity<Path> getPath(@PathVariable String id) {
        log.debug("REST request to get Path : {}", id);
        Optional<Path> path = pathRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(path);
    }

    /**
     * {@code DELETE  /paths/:id} : delete the "id" path.
     *
     * @param id the id of the path to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paths/{id}")
    public ResponseEntity<Void> deletePath(@PathVariable String id) {
        log.debug("REST request to delete Path : {}", id);
        pathRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
