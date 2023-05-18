package com.modis.edu.service.impl;

import com.modis.edu.domain.Learner;
import com.modis.edu.repository.LearnerRepository;
import com.modis.edu.service.LearnerService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Learner}.
 */
@Service
public class LearnerServiceImpl implements LearnerService {

    private final Logger log = LoggerFactory.getLogger(LearnerServiceImpl.class);

    private final LearnerRepository learnerRepository;

    public LearnerServiceImpl(LearnerRepository learnerRepository) {
        this.learnerRepository = learnerRepository;
    }

    @Override
    public Learner save(Learner learner) {
        log.debug("Request to save Learner : {}", learner);
        return learnerRepository.save(learner);
    }

    @Override
    public Learner update(Learner learner) {
        log.debug("Request to update Learner : {}", learner);
        return learnerRepository.save(learner);
    }

    @Override
    public Optional<Learner> partialUpdate(Learner learner) {
        log.debug("Request to partially update Learner : {}", learner);

        return learnerRepository
            .findById(learner.getId())
            .map(existingLearner -> {
                if (learner.getFirstName() != null) {
                    existingLearner.setFirstName(learner.getFirstName());
                }
                if (learner.getLastName() != null) {
                    existingLearner.setLastName(learner.getLastName());
                }
                if (learner.getEmail() != null) {
                    existingLearner.setEmail(learner.getEmail());
                }
                if (learner.getPhoneNumber() != null) {
                    existingLearner.setPhoneNumber(learner.getPhoneNumber());
                }

                return existingLearner;
            })
            .map(learnerRepository::save);
    }

    @Override
    public Page<Learner> findAll(Pageable pageable) {
        log.debug("Request to get all Learners");
        return learnerRepository.findAll(pageable);
    }

    @Override
    public Optional<Learner> findOne(String id) {
        log.debug("Request to get Learner : {}", id);
        return learnerRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Learner : {}", id);
        learnerRepository.deleteById(id);
    }
}
