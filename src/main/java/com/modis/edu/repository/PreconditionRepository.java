package com.modis.edu.repository;

import com.modis.edu.domain.Precondition;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Precondition entity.
 */
@Repository
public interface PreconditionRepository extends MongoRepository<Precondition, String> {
    @Query("{}")
    Page<Precondition> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Precondition> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Precondition> findOneWithEagerRelationships(String id);
}
