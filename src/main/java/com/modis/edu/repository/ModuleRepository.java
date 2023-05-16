package com.modis.edu.repository;

import com.modis.edu.domain.Module;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Module entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleRepository extends MongoRepository<Module, String> {}
