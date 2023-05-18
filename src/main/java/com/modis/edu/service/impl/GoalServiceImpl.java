package com.modis.edu.service.impl;

import com.modis.edu.domain.Goal;
import com.modis.edu.repository.GoalRepository;
import com.modis.edu.service.GoalService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Goal}.
 */
@Service
public class GoalServiceImpl implements GoalService {

    private final Logger log = LoggerFactory.getLogger(GoalServiceImpl.class);

    private final GoalRepository goalRepository;

    public GoalServiceImpl(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    public Goal save(Goal goal) {
        log.debug("Request to save Goal : {}", goal);
        return goalRepository.save(goal);
    }

    @Override
    public Goal update(Goal goal) {
        log.debug("Request to update Goal : {}", goal);
        return goalRepository.save(goal);
    }

    @Override
    public Optional<Goal> partialUpdate(Goal goal) {
        log.debug("Request to partially update Goal : {}", goal);

        return goalRepository
            .findById(goal.getId())
            .map(existingGoal -> {
                if (goal.getTitle() != null) {
                    existingGoal.setTitle(goal.getTitle());
                }

                return existingGoal;
            })
            .map(goalRepository::save);
    }

    @Override
    public List<Goal> findAll() {
        log.debug("Request to get all Goals");
        return goalRepository.findAll();
    }

    public Page<Goal> findAllWithEagerRelationships(Pageable pageable) {
        return goalRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    public Optional<Goal> findOne(String id) {
        log.debug("Request to get Goal : {}", id);
        return goalRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Goal : {}", id);
        goalRepository.deleteById(id);
    }
}
