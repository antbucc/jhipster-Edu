package com.modis.edu.repository;

import com.modis.edu.domain.Domain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Domain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DomainRepository extends MongoRepository<Domain, String> {}
