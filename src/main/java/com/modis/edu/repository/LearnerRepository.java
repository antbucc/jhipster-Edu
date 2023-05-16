package com.modis.edu.repository;

import com.modis.edu.domain.Learner;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Learner entity.
 */
@Repository
public interface LearnerRepository extends MongoRepository<Learner, String> {
    @Query("{}")
    Page<Learner> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Learner> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Learner> findOneWithEagerRelationships(String id);
}
