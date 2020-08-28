package com.prodyna.pac.pacbackend.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.prodyna.pac.pacbackend.model.Topic;

import io.micrometer.core.annotation.Timed;

@RepositoryRestResource(path = "topics", collectionResourceRel = "topics")
@Timed
public interface TopicRepository extends Neo4jRepository<Topic, Long> {

}
