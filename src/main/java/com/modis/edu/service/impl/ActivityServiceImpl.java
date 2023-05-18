package com.modis.edu.service.impl;

import com.modis.edu.domain.Activity;
import com.modis.edu.repository.ActivityRepository;
import com.modis.edu.service.ActivityService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Activity}.
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    private final Logger log = LoggerFactory.getLogger(ActivityServiceImpl.class);

    private final ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public Activity save(Activity activity) {
        log.debug("Request to save Activity : {}", activity);
        return activityRepository.save(activity);
    }

    @Override
    public Activity update(Activity activity) {
        log.debug("Request to update Activity : {}", activity);
        return activityRepository.save(activity);
    }

    @Override
    public Optional<Activity> partialUpdate(Activity activity) {
        log.debug("Request to partially update Activity : {}", activity);

        return activityRepository
            .findById(activity.getId())
            .map(existingActivity -> {
                if (activity.getTitle() != null) {
                    existingActivity.setTitle(activity.getTitle());
                }
                if (activity.getDescription() != null) {
                    existingActivity.setDescription(activity.getDescription());
                }
                if (activity.getType() != null) {
                    existingActivity.setType(activity.getType());
                }
                if (activity.getTool() != null) {
                    existingActivity.setTool(activity.getTool());
                }
                if (activity.getDifficulty() != null) {
                    existingActivity.setDifficulty(activity.getDifficulty());
                }

                return existingActivity;
            })
            .map(activityRepository::save);
    }

    @Override
    public List<Activity> findAll() {
        log.debug("Request to get all Activities");
        return activityRepository.findAll();
    }

    public Page<Activity> findAllWithEagerRelationships(Pageable pageable) {
        return activityRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    public Optional<Activity> findOne(String id) {
        log.debug("Request to get Activity : {}", id);
        return activityRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Activity : {}", id);
        activityRepository.deleteById(id);
    }
}
