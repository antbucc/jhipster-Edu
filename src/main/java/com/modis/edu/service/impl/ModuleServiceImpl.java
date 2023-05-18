package com.modis.edu.service.impl;

import com.modis.edu.domain.Module;
import com.modis.edu.repository.ModuleRepository;
import com.modis.edu.service.ModuleService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Module}.
 */
@Service
public class ModuleServiceImpl implements ModuleService {

    private final Logger log = LoggerFactory.getLogger(ModuleServiceImpl.class);

    private final ModuleRepository moduleRepository;

    public ModuleServiceImpl(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @Override
    public Module save(Module module) {
        log.debug("Request to save Module : {}", module);
        return moduleRepository.save(module);
    }

    @Override
    public Module update(Module module) {
        log.debug("Request to update Module : {}", module);
        return moduleRepository.save(module);
    }

    @Override
    public Optional<Module> partialUpdate(Module module) {
        log.debug("Request to partially update Module : {}", module);

        return moduleRepository
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
    }

    @Override
    public List<Module> findAll() {
        log.debug("Request to get all Modules");
        return moduleRepository.findAll();
    }

    public Page<Module> findAllWithEagerRelationships(Pageable pageable) {
        return moduleRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    public Optional<Module> findOne(String id) {
        log.debug("Request to get Module : {}", id);
        return moduleRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Module : {}", id);
        moduleRepository.deleteById(id);
    }
}
