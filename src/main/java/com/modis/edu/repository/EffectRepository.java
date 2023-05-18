package com.modis.edu.repository;

import com.modis.edu.domain.Effect;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Effect entity.
 */
@Repository
public interface EffectRepository extends MongoRepository<Effect, String> {
    @Query("{}")
    Page<Effect> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Effect> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Effect> findOneWithEagerRelationships(String id);
}
