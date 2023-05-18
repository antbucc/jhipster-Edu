package com.modis.edu.service.impl;

import com.modis.edu.domain.Scenario;
import com.modis.edu.repository.ScenarioRepository;
import com.modis.edu.service.ScenarioService;
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
 * Service Implementation for managing {@link Scenario}.
 */
@Service
public class ScenarioServiceImpl implements ScenarioService {

    private final Logger log = LoggerFactory.getLogger(ScenarioServiceImpl.class);

    private final ScenarioRepository scenarioRepository;

    public ScenarioServiceImpl(ScenarioRepository scenarioRepository) {
        this.scenarioRepository = scenarioRepository;
    }

    @Override
    public Scenario save(Scenario scenario) {
        log.debug("Request to save Scenario : {}", scenario);
        return scenarioRepository.save(scenario);
    }

    @Override
    public Scenario update(Scenario scenario) {
        log.debug("Request to update Scenario : {}", scenario);
        return scenarioRepository.save(scenario);
    }

    @Override
    public Optional<Scenario> partialUpdate(Scenario scenario) {
        log.debug("Request to partially update Scenario : {}", scenario);

        return scenarioRepository
            .findById(scenario.getId())
            .map(existingScenario -> {
                if (scenario.getTitle() != null) {
                    existingScenario.setTitle(scenario.getTitle());
                }
                if (scenario.getDescription() != null) {
                    existingScenario.setDescription(scenario.getDescription());
                }
                if (scenario.getLanguage() != null) {
                    existingScenario.setLanguage(scenario.getLanguage());
                }

                return existingScenario;
            })
            .map(scenarioRepository::save);
    }

    @Override
    public Page<Scenario> findAll(Pageable pageable) {
        log.debug("Request to get all Scenarios");
        return scenarioRepository.findAll(pageable);
    }

    public Page<Scenario> findAllWithEagerRelationships(Pageable pageable) {
        return scenarioRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     *  Get all the scenarios where Module is {@code null}.
     *  @return the list of entities.
     */

    public List<Scenario> findAllWhereModuleIsNull() {
        log.debug("Request to get all scenarios where Module is null");
        return StreamSupport
            .stream(scenarioRepository.findAll().spliterator(), false)
            .filter(scenario -> scenario.getModule() == null)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Scenario> findOne(String id) {
        log.debug("Request to get Scenario : {}", id);
        return scenarioRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Scenario : {}", id);
        scenarioRepository.deleteById(id);
    }
}
