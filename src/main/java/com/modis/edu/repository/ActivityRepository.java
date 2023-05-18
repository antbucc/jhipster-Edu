package com.modis.edu.repository;

import com.modis.edu.domain.Activity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Activity entity.
 */
@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {
    @Query("{}")
    Page<Activity> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Activity> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Activity> findOneWithEagerRelationships(String id);
}
