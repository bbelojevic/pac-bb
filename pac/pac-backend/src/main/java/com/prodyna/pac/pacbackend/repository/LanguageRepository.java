package com.prodyna.pac.pacbackend.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.prodyna.pac.pacbackend.model.Language;

import io.micrometer.core.annotation.Timed;

@RepositoryRestResource(path = "languages", collectionResourceRel = "languages")
@Timed
public interface LanguageRepository extends Neo4jRepository<Language, Long> {

}
