package com.prodyna.pac.pacbackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prodyna.pac.pacbackend.exception.LocationException;
import com.prodyna.pac.pacbackend.model.Location;
import com.prodyna.pac.pacbackend.repository.LocationRepository;

import io.micrometer.core.annotation.Timed;

@CrossOrigin
@RestController
@Timed
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    private static final String template = "Hello, %s!";

    @GetMapping("/greeting")
    public Location greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Location(String.format(template, name));
    }

    @GetMapping(path = "/createLocation")
    public HttpStatus createLocation(@RequestParam String name) {

        this.locationRepository.save(new Location(name));

        return HttpStatus.CREATED;

    }

    @Timed("pac.locations.getall")
    @GetMapping(path = "/locations")
    public List<Location> getLocations() {

        return (List<Location>) this.locationRepository.findAll();

    }

    @PostMapping("/location")
    public Location newLocation(@RequestBody Location newLocation) {

        return this.locationRepository.save(newLocation);

    }

    @Timed("pac.locations.getone")
    @GetMapping("/locations/{id}")
    public Location getLocation(@PathVariable Long id) {

        return this.locationRepository.findById(id).orElseThrow(() -> new LocationException("Can't find location with id " + id));

    }

    @Timed("pac.locations.createorupdate")
    @PutMapping("/locations/{id}")
    public ResponseEntity<Location> updateOrCreateLocation(@RequestBody Location newLocation, @PathVariable Long id) {

        Location locationUpdatedOrCreated = this.locationRepository.findById(id).map(location -> {
            location.setName(newLocation.getName());
            return this.locationRepository.save(location);
        }).orElseGet(() -> {
            newLocation.setId(id);
            return this.locationRepository.save(newLocation);
        });

        return new ResponseEntity<Location>(locationUpdatedOrCreated, HttpStatus.OK);

    }

    @Timed("pac.locations.delete")
    @DeleteMapping("/locations/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {

        Optional<Location> location = this.locationRepository.findById(id);

        if (location.isPresent()) {
            this.locationRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/locations/findByName")
    public List<Location> findByName(@RequestParam String name) {

        // StringBuilder stringBuilder = new StringBuilder("Locations with name: " + name + "\n");

        // this.locationRepository.findByName(name).forEach(p -> stringBuilder.append(p.toString()).append("\n"));

        return this.locationRepository.findByName(name);

    }

}
