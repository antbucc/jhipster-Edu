package com.modis.edu.repository;

import com.modis.edu.domain.Scenario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Scenario entity.
 */
@Repository
public interface ScenarioRepository extends MongoRepository<Scenario, String> {
    @Query("{}")
    Page<Scenario> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Scenario> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Scenario> findOneWithEagerRelationships(String id);
}
