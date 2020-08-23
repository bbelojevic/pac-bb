package com.prodyna.pac.pacbackend.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.prodyna.pac.pacbackend.model.Event;
import com.prodyna.pac.pacbackend.model.Talk;
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
    @Query("match (event:Event)--(talk:Talk)--(topic:Topic) where id(event) = {eventId} return distinct(topic)")
    Set<Topic> findTopicsForEvent(@Param("eventId") long eventId);

    @Timed("pac.talks.gettalks")
    @RestResource(path = "talks", rel = "talks")
    @Query("match (event:Event)--(talk:Talk)-[rlevel:IS_LEVEL]-(level:Level), "
            + "(t)-[rlanguage:ON_LANGUAGE]-(language:Language), "
            + "(t)-[rperson:BY_PERSON]-(person:Person), "
            + "(t)-[rtopic:IS_USED]-(topic:Topic) "
            + "where id(event) = {eventId} "
            + "return talk, rlevel, level, rlanguage, language, rperson, person, rtopic, topic "
            + "order by talk.startDate")
    @Depth(1)
    Set<Talk> findTalksForEvent(@Param("eventId") long eventId);

}
