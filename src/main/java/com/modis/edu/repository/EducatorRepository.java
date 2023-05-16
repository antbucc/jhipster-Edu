package com.modis.edu.repository;

import com.modis.edu.domain.Educator;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Educator entity.
 */
@Repository
public interface EducatorRepository extends MongoRepository<Educator, String> {
    @Query("{}")
    Page<Educator> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Educator> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Educator> findOneWithEagerRelationships(String id);
}
