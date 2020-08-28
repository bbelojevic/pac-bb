package com.prodyna.pac.pacbackend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prodyna.pac.pacbackend.model.Location;
import com.prodyna.pac.pacbackend.repository.LocationRepository;

import io.micrometer.core.annotation.Timed;

@RepositoryRestController
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @Timed("pac.locations.deletewitheventsandtalks")
    @RequestMapping(method = RequestMethod.DELETE, value = "/locations/deleteWithEventsAndTalks/{id}")
    public ResponseEntity<Void> deleteLocationWithEventsAndTalks(@PathVariable Long id) {

        Optional<Location> location = this.locationRepository.findById(id);

        if (location.isPresent()) {

            long count = this.locationRepository.countLocations(id);

            if (count == 0) {
                this.locationRepository.deleteById(id);
            } else {
                this.locationRepository.deleteWithEventsAndTalks(id);
            }

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

    }

}
