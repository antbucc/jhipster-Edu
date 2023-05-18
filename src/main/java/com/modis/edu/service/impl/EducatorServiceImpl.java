package com.modis.edu.service.impl;

import com.modis.edu.domain.Educator;
import com.modis.edu.repository.EducatorRepository;
import com.modis.edu.service.EducatorService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Educator}.
 */
@Service
public class EducatorServiceImpl implements EducatorService {

    private final Logger log = LoggerFactory.getLogger(EducatorServiceImpl.class);

    private final EducatorRepository educatorRepository;

    public EducatorServiceImpl(EducatorRepository educatorRepository) {
        this.educatorRepository = educatorRepository;
    }

    @Override
    public Educator save(Educator educator) {
        log.debug("Request to save Educator : {}", educator);
        return educatorRepository.save(educator);
    }

    @Override
    public Educator update(Educator educator) {
        log.debug("Request to update Educator : {}", educator);
        return educatorRepository.save(educator);
    }

    @Override
    public Optional<Educator> partialUpdate(Educator educator) {
        log.debug("Request to partially update Educator : {}", educator);

        return educatorRepository
            .findById(educator.getId())
            .map(existingEducator -> {
                if (educator.getFirstName() != null) {
                    existingEducator.setFirstName(educator.getFirstName());
                }
                if (educator.getLastName() != null) {
                    existingEducator.setLastName(educator.getLastName());
                }
                if (educator.getEmail() != null) {
                    existingEducator.setEmail(educator.getEmail());
                }

                return existingEducator;
            })
            .map(educatorRepository::save);
    }

    @Override
    public Page<Educator> findAll(Pageable pageable) {
        log.debug("Request to get all Educators");
        return educatorRepository.findAll(pageable);
    }

    @Override
    public Optional<Educator> findOne(String id) {
        log.debug("Request to get Educator : {}", id);
        return educatorRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Educator : {}", id);
        educatorRepository.deleteById(id);
    }
}
