package com.modis.edu.service.impl;

import com.modis.edu.domain.Precondition;
import com.modis.edu.repository.PreconditionRepository;
import com.modis.edu.service.PreconditionService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Precondition}.
 */
@Service
public class PreconditionServiceImpl implements PreconditionService {

    private final Logger log = LoggerFactory.getLogger(PreconditionServiceImpl.class);

    private final PreconditionRepository preconditionRepository;

    public PreconditionServiceImpl(PreconditionRepository preconditionRepository) {
        this.preconditionRepository = preconditionRepository;
    }

    @Override
    public Precondition save(Precondition precondition) {
        log.debug("Request to save Precondition : {}", precondition);
        return preconditionRepository.save(precondition);
    }

    @Override
    public Precondition update(Precondition precondition) {
        log.debug("Request to update Precondition : {}", precondition);
        return preconditionRepository.save(precondition);
    }

    @Override
    public Optional<Precondition> partialUpdate(Precondition precondition) {
        log.debug("Request to partially update Precondition : {}", precondition);

        return preconditionRepository
            .findById(precondition.getId())
            .map(existingPrecondition -> {
                if (precondition.getTitle() != null) {
                    existingPrecondition.setTitle(precondition.getTitle());
                }

                return existingPrecondition;
            })
            .map(preconditionRepository::save);
    }

    @Override
    public List<Precondition> findAll() {
        log.debug("Request to get all Preconditions");
        return preconditionRepository.findAll();
    }

    public Page<Precondition> findAllWithEagerRelationships(Pageable pageable) {
        return preconditionRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    public Optional<Precondition> findOne(String id) {
        log.debug("Request to get Precondition : {}", id);
        return preconditionRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Precondition : {}", id);
        preconditionRepository.deleteById(id);
    }
}
