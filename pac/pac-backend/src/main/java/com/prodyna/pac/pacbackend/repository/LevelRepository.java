package com.prodyna.pac.pacbackend.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.prodyna.pac.pacbackend.model.Level;

import io.micrometer.core.annotation.Timed;

@RepositoryRestResource(path = "levels", collectionResourceRel = "levels")
@Timed
public interface LevelRepository extends Neo4jRepository<Level, Long> {

}
