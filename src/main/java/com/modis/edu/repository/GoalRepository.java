package com.modis.edu.repository;

import com.modis.edu.domain.Goal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Goal entity.
 */
@Repository
public interface GoalRepository extends MongoRepository<Goal, String> {
    @Query("{}")
    Page<Goal> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Goal> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Goal> findOneWithEagerRelationships(String id);
}
