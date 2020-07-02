package com.prodyna.pac.pacbackend.repository;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.prodyna.pac.pacbackend.model.Location;

public interface LocationRepository extends Neo4jRepository<Location, Long> {

    List<Location> findByName(@Param("name") String name);

}
