package com.modis.edu.service.impl;

import com.modis.edu.domain.Domain;
import com.modis.edu.repository.DomainRepository;
import com.modis.edu.service.DomainService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Domain}.
 */
@Service
public class DomainServiceImpl implements DomainService {

    private final Logger log = LoggerFactory.getLogger(DomainServiceImpl.class);

    private final DomainRepository domainRepository;

    public DomainServiceImpl(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }

    @Override
    public Domain save(Domain domain) {
        log.debug("Request to save Domain : {}", domain);
        return domainRepository.save(domain);
    }

    @Override
    public Domain update(Domain domain) {
        log.debug("Request to update Domain : {}", domain);
        return domainRepository.save(domain);
    }

    @Override
    public Optional<Domain> partialUpdate(Domain domain) {
        log.debug("Request to partially update Domain : {}", domain);

        return domainRepository
            .findById(domain.getId())
            .map(existingDomain -> {
                if (domain.getTitle() != null) {
                    existingDomain.setTitle(domain.getTitle());
                }
                if (domain.getDescription() != null) {
                    existingDomain.setDescription(domain.getDescription());
                }
                if (domain.getCity() != null) {
                    existingDomain.setCity(domain.getCity());
                }

                return existingDomain;
            })
            .map(domainRepository::save);
    }

    @Override
    public Page<Domain> findAll(Pageable pageable) {
        log.debug("Request to get all Domains");
        return domainRepository.findAll(pageable);
    }

    /**
     *  Get all the domains where Scenario is {@code null}.
     *  @return the list of entities.
     */

    public List<Domain> findAllWhereScenarioIsNull() {
        log.debug("Request to get all domains where Scenario is null");
        return StreamSupport
            .stream(domainRepository.findAll().spliterator(), false)
            .filter(domain -> domain.getScenario() == null)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Domain> findOne(String id) {
        log.debug("Request to get Domain : {}", id);
        return domainRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Domain : {}", id);
        domainRepository.deleteById(id);
    }
}
