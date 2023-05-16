package com.modis.edu.repository;

import com.modis.edu.domain.Domain;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Domain entity.
 */
@Repository
public interface DomainRepository extends MongoRepository<Domain, String> {
    @Query("{}")
    Page<Domain> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Domain> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Domain> findOneWithEagerRelationships(String id);
}
