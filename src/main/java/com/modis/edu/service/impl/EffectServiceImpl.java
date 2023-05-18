package com.modis.edu.service.impl;

import com.modis.edu.domain.Effect;
import com.modis.edu.repository.EffectRepository;
import com.modis.edu.service.EffectService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Effect}.
 */
@Service
public class EffectServiceImpl implements EffectService {

    private final Logger log = LoggerFactory.getLogger(EffectServiceImpl.class);

    private final EffectRepository effectRepository;

    public EffectServiceImpl(EffectRepository effectRepository) {
        this.effectRepository = effectRepository;
    }

    @Override
    public Effect save(Effect effect) {
        log.debug("Request to save Effect : {}", effect);
        return effectRepository.save(effect);
    }

    @Override
    public Effect update(Effect effect) {
        log.debug("Request to update Effect : {}", effect);
        return effectRepository.save(effect);
    }

    @Override
    public Optional<Effect> partialUpdate(Effect effect) {
        log.debug("Request to partially update Effect : {}", effect);

        return effectRepository
            .findById(effect.getId())
            .map(existingEffect -> {
                if (effect.getTitle() != null) {
                    existingEffect.setTitle(effect.getTitle());
                }

                return existingEffect;
            })
            .map(effectRepository::save);
    }

    @Override
    public List<Effect> findAll() {
        log.debug("Request to get all Effects");
        return effectRepository.findAll();
    }

    public Page<Effect> findAllWithEagerRelationships(Pageable pageable) {
        return effectRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    public Optional<Effect> findOne(String id) {
        log.debug("Request to get Effect : {}", id);
        return effectRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Effect : {}", id);
        effectRepository.deleteById(id);
    }
}
