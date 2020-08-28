package com.prodyna.pac.pacbackend.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.prodyna.pac.pacbackend.model.Room;

import io.micrometer.core.annotation.Timed;

@RepositoryRestResource(path = "rooms", collectionResourceRel = "rooms")
@Timed
public interface RoomRepository extends Neo4jRepository<Room, Long> {

}
