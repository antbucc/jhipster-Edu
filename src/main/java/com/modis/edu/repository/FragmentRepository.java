package com.modis.edu.repository;

import com.modis.edu.domain.Fragment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Fragment entity.
 */
@Repository
public interface FragmentRepository extends MongoRepository<Fragment, String> {
    @Query("{}")
    Page<Fragment> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Fragment> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Fragment> findOneWithEagerRelationships(String id);
}
