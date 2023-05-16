package com.modis.edu.repository;

import com.modis.edu.domain.Educator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Educator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EducatorRepository extends MongoRepository<Educator, String> {}
