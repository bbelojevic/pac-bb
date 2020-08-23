package com.prodyna.pac.pacbackend.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.prodyna.pac.pacbackend.model.Event;
import com.prodyna.pac.pacbackend.model.Topic;
import com.prodyna.pac.pacbackend.projection.EventProjection;

import io.micrometer.core.annotation.Timed;

@Repository
@RepositoryRestResource(path = "events", collectionResourceRel = "events", excerptProjection = EventProjection.class)
@CrossOrigin
@Timed
public interface EventRepository extends Neo4jRepository<Event, Long> {

    @Override
    @Timed("pac.events.getall")
    Iterable<Event> findAll();

    @Timed("pac.events.gettopics")
    @RestResource(exported = false)
    @Query("match (e:Event)--(t:Talk)--(to:Topic) where id(e) = {eventId} return distinct(to)")
    Set<Topic> findTopicsForEvent(@Param("eventId") long eventId);

}
