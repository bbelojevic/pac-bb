package com.prodyna.pac.pacbackend.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.prodyna.pac.pacbackend.model.Location;

import io.micrometer.core.annotation.Timed;

@RepositoryRestResource(path = "locations", collectionResourceRel = "locations")
@CrossOrigin
@Timed
public interface LocationRepository extends Neo4jRepository<Location, Long> {

    @Override
    @Timed("pac.locations.getall")
    Iterable<Location> findAll();
    
    @Override
    @Timed("pac.locations.delete")
    void deleteById(Long id);
    
    @Override
    @Timed("pac.locations.save")
    <S extends Location> S save(S entity);

    @RestResource(exported = false)
    @Query("match (location:Location)-[rlocation:IN_LOCATION]-(event:Event), "
            + "(event)-[revent:ON_EVENT]-(talk:Talk) "
            + "where id(location) = {locationId} "
            + "detach delete location, event, talk")
    void deleteWithEventsAndTalks(long locationId);
    
    @RestResource(exported = false)
    @Query("match (location:Location)-[rlocation:IN_LOCATION]-(event:Event) "
            + "where id(location) = {locationId} "
            + "return count(location) as c")
    long countLocations(long locationId);

}
