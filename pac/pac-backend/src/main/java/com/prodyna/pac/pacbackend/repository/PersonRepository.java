package com.prodyna.pac.pacbackend.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.prodyna.pac.pacbackend.model.Person;
import com.prodyna.pac.pacbackend.projection.PersonProjection;

import io.micrometer.core.annotation.Timed;

@Repository
@RepositoryRestResource(path="persons", collectionResourceRel = "persons", excerptProjection = PersonProjection.class)
@CrossOrigin
@Timed
public interface PersonRepository extends Neo4jRepository<Person, Long> {

    @Override
    @Timed("pac.persons.getall")
    Iterable<Person> findAll();
    
}
