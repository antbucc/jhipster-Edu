package com.modis.edu.service.impl;

import com.modis.edu.domain.Fragment;
import com.modis.edu.repository.FragmentRepository;
import com.modis.edu.service.FragmentService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Fragment}.
 */
@Service
public class FragmentServiceImpl implements FragmentService {

    private final Logger log = LoggerFactory.getLogger(FragmentServiceImpl.class);

    private final FragmentRepository fragmentRepository;

    public FragmentServiceImpl(FragmentRepository fragmentRepository) {
        this.fragmentRepository = fragmentRepository;
    }

    @Override
    public Fragment save(Fragment fragment) {
        log.debug("Request to save Fragment : {}", fragment);
        return fragmentRepository.save(fragment);
    }

    @Override
    public Fragment update(Fragment fragment) {
        log.debug("Request to update Fragment : {}", fragment);
        return fragmentRepository.save(fragment);
    }

    @Override
    public Optional<Fragment> partialUpdate(Fragment fragment) {
        log.debug("Request to partially update Fragment : {}", fragment);

        return fragmentRepository
            .findById(fragment.getId())
            .map(existingFragment -> {
                if (fragment.getTitle() != null) {
                    existingFragment.setTitle(fragment.getTitle());
                }

                return existingFragment;
            })
            .map(fragmentRepository::save);
    }

    @Override
    public List<Fragment> findAll() {
        log.debug("Request to get all Fragments");
        return fragmentRepository.findAll();
    }

    public Page<Fragment> findAllWithEagerRelationships(Pageable pageable) {
        return fragmentRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    public Optional<Fragment> findOne(String id) {
        log.debug("Request to get Fragment : {}", id);
        return fragmentRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Fragment : {}", id);
        fragmentRepository.deleteById(id);
    }
}
