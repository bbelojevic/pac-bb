package com.prodyna.pac.pacbackend.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.prodyna.pac.pacbackend.model.Talk;
import com.prodyna.pac.pacbackend.projection.TalkProjection;

import io.micrometer.core.annotation.Timed;

@Repository
@RepositoryRestResource(path = "talks", collectionResourceRel = "talks", excerptProjection = TalkProjection.class)
@CrossOrigin
@Timed
public interface TalkRepository extends Neo4jRepository<Talk, Long> {

    @Override
    @Timed("pac.talks.getall")
    Iterable<Talk> findAll();

}
