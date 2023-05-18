package com.modis.edu.service.impl;

import com.modis.edu.domain.Concept;
import com.modis.edu.repository.ConceptRepository;
import com.modis.edu.service.ConceptService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Concept}.
 */
@Service
public class ConceptServiceImpl implements ConceptService {

    private final Logger log = LoggerFactory.getLogger(ConceptServiceImpl.class);

    private final ConceptRepository conceptRepository;

    public ConceptServiceImpl(ConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    @Override
    public Concept save(Concept concept) {
        log.debug("Request to save Concept : {}", concept);
        return conceptRepository.save(concept);
    }

    @Override
    public Concept update(Concept concept) {
        log.debug("Request to update Concept : {}", concept);
        return conceptRepository.save(concept);
    }

    @Override
    public Optional<Concept> partialUpdate(Concept concept) {
        log.debug("Request to partially update Concept : {}", concept);

        return conceptRepository
            .findById(concept.getId())
            .map(existingConcept -> {
                if (concept.getTitle() != null) {
                    existingConcept.setTitle(concept.getTitle());
                }
                if (concept.getDescription() != null) {
                    existingConcept.setDescription(concept.getDescription());
                }

                return existingConcept;
            })
            .map(conceptRepository::save);
    }

    @Override
    public List<Concept> findAll() {
        log.debug("Request to get all Concepts");
        return conceptRepository.findAll();
    }

    @Override
    public Optional<Concept> findOne(String id) {
        log.debug("Request to get Concept : {}", id);
        return conceptRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Concept : {}", id);
        conceptRepository.deleteById(id);
    }
}
