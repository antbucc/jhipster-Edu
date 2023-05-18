package com.modis.edu.web.rest;

import com.modis.edu.domain.Domain;
import com.modis.edu.repository.DomainRepository;
import com.modis.edu.service.DomainService;
import com.modis.edu.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.modis.edu.domain.Domain}.
 */
@RestController
@RequestMapping("/api")
public class DomainResource {

    private final Logger log = LoggerFactory.getLogger(DomainResource.class);

    private static final String ENTITY_NAME = "domain";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DomainService domainService;

    private final DomainRepository domainRepository;

    public DomainResource(DomainService domainService, DomainRepository domainRepository) {
        this.domainService = domainService;
        this.domainRepository = domainRepository;
    }

    /**
     * {@code POST  /domains} : Create a new domain.
     *
     * @param domain the domain to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new domain, or with status {@code 400 (Bad Request)} if the domain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/domains")
    public ResponseEntity<Domain> createDomain(@RequestBody Domain domain) throws URISyntaxException {
        log.debug("REST request to save Domain : {}", domain);
        if (domain.getId() != null) {
            throw new BadRequestAlertException("A new domain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Domain result = domainService.save(domain);
        return ResponseEntity
            .created(new URI("/api/domains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /domains/:id} : Updates an existing domain.
     *
     * @param id the id of the domain to save.
     * @param domain the domain to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated domain,
     * or with status {@code 400 (Bad Request)} if the domain is not valid,
     * or with status {@code 500 (Internal Server Error)} if the domain couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/domains/{id}")
    public ResponseEntity<Domain> updateDomain(@PathVariable(value = "id", required = false) final String id, @RequestBody Domain domain)
        throws URISyntaxException {
        log.debug("REST request to update Domain : {}, {}", id, domain);
        if (domain.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, domain.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!domainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Domain result = domainService.update(domain);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, domain.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /domains/:id} : Partial updates given fields of an existing domain, field will ignore if it is null
     *
     * @param id the id of the domain to save.
     * @param domain the domain to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated domain,
     * or with status {@code 400 (Bad Request)} if the domain is not valid,
     * or with status {@code 404 (Not Found)} if the domain is not found,
     * or with status {@code 500 (Internal Server Error)} if the domain couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/domains/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Domain> partialUpdateDomain(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Domain domain
    ) throws URISyntaxException {
        log.debug("REST request to partial update Domain partially : {}, {}", id, domain);
        if (domain.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, domain.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!domainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Domain> result = domainService.partialUpdate(domain);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, domain.getId()));
    }

    /**
     * {@code GET  /domains} : get all the domains.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of domains in body.
     */
    @GetMapping("/domains")
    public ResponseEntity<List<Domain>> getAllDomains(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("scenario-is-null".equals(filter)) {
            log.debug("REST request to get all Domains where scenario is null");
            return new ResponseEntity<>(domainService.findAllWhereScenarioIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Domains");
        Page<Domain> page = domainService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /domains/:id} : get the "id" domain.
     *
     * @param id the id of the domain to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the domain, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/domains/{id}")
    public ResponseEntity<Domain> getDomain(@PathVariable String id) {
        log.debug("REST request to get Domain : {}", id);
        Optional<Domain> domain = domainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(domain);
    }

    /**
     * {@code DELETE  /domains/:id} : delete the "id" domain.
     *
     * @param id the id of the domain to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/domains/{id}")
    public ResponseEntity<Void> deleteDomain(@PathVariable String id) {
        log.debug("REST request to delete Domain : {}", id);
        domainService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
