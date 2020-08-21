package com.prodyna.pac.pacbackend.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.prodyna.pac.pacbackend.model.Event;

import io.micrometer.core.annotation.Timed;

@Repository
@RepositoryRestResource(path="events", collectionResourceRel = "events")
@Timed
public interface EventRepository extends Neo4jRepository<Event, Long> {

    @Override
    @Timed("pac.events.getall")
    Iterable<Event> findAll();
    
}
