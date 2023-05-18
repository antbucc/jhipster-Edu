package com.modis.edu.repository;

import com.modis.edu.domain.Path;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Path entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PathRepository extends MongoRepository<Path, String> {}
