package com.modis.edu.repository;

import com.modis.edu.domain.Competence;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Competence entity.
 */
@Repository
public interface CompetenceRepository extends MongoRepository<Competence, String> {
    @Query("{}")
    Page<Competence> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Competence> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Competence> findOneWithEagerRelationships(String id);
}
