package com.modis.edu.repository;

import com.modis.edu.domain.Concept;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Concept entity.
 */
@Repository
public interface ConceptRepository extends MongoRepository<Concept, String> {
    @Query("{}")
    Page<Concept> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Concept> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Concept> findOneWithEagerRelationships(String id);
}
