package com.modis.edu.service.impl;

import com.modis.edu.domain.Condition;
import com.modis.edu.repository.ConditionRepository;
import com.modis.edu.service.ConditionService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Condition}.
 */
@Service
public class ConditionServiceImpl implements ConditionService {

    private final Logger log = LoggerFactory.getLogger(ConditionServiceImpl.class);

    private final ConditionRepository conditionRepository;

    public ConditionServiceImpl(ConditionRepository conditionRepository) {
        this.conditionRepository = conditionRepository;
    }

    @Override
    public Condition save(Condition condition) {
        log.debug("Request to save Condition : {}", condition);
        return conditionRepository.save(condition);
    }

    @Override
    public Condition update(Condition condition) {
        log.debug("Request to update Condition : {}", condition);
        return conditionRepository.save(condition);
    }

    @Override
    public Optional<Condition> partialUpdate(Condition condition) {
        log.debug("Request to partially update Condition : {}", condition);

        return conditionRepository
            .findById(condition.getId())
            .map(existingCondition -> {
                if (condition.getTitle() != null) {
                    existingCondition.setTitle(condition.getTitle());
                }
                if (condition.getType() != null) {
                    existingCondition.setType(condition.getType());
                }

                return existingCondition;
            })
            .map(conditionRepository::save);
    }

    @Override
    public List<Condition> findAll() {
        log.debug("Request to get all Conditions");
        return conditionRepository.findAll();
    }

    @Override
    public Optional<Condition> findOne(String id) {
        log.debug("Request to get Condition : {}", id);
        return conditionRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Condition : {}", id);
        conditionRepository.deleteById(id);
    }
}
