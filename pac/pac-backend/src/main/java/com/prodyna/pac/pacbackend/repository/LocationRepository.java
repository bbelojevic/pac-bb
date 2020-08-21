package com.prodyna.pac.pacbackend.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.prodyna.pac.pacbackend.model.Location;

public interface LocationRepository extends Neo4jRepository<Location, Long> {

}
