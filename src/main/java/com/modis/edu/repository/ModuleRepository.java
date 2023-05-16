package com.modis.edu.repository;

import com.modis.edu.domain.Module;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Module entity.
 */
@Repository
public interface ModuleRepository extends MongoRepository<Module, String> {
    @Query("{}")
    Page<Module> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Module> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Module> findOneWithEagerRelationships(String id);
}
