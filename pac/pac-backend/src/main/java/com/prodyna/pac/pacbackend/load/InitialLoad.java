package com.prodyna.pac.pacbackend.load;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.prodyna.pac.pacbackend.repository.EventRepository;
import com.prodyna.pac.pacbackend.repository.LocationRepository;
import com.prodyna.pac.pacbackend.repository.OrganizationRepository;
import com.prodyna.pac.pacbackend.repository.PersonRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class InitialLoad {

    @Bean
    CommandLineRunner initDatabase(final LocationRepository locationRepository, final EventRepository eventRepository, final OrganizationRepository organizationRepository, final PersonRepository personRepository) {
    
        /*Location location1 = locationRepository.save(new Location("Belgrade"));
        Location location2 = locationRepository.save(new Location("Frankfurt"));
        
        log.info("Preloading " + location1);
        log.info("Preloading " + location2);
        
        Event event1 = new Event();
        event1.setName("Container days");
        event1.setStartDate(new Date());
        event1.setEndDate(new Date());
        event1.setLocation(location1);
        
        event1 = eventRepository.save(event1);
        
        Event event2 = new Event();
        event2.setName("Heapcon");
        event2.setStartDate(new Date());
        event2.setEndDate(new Date());
        event2.setLocation(location2);
        
        event2 = eventRepository.save(event2);
        
        log.info("Preloading " + event1);
        log.info("Preloading " + event2);*/
        
        /*Organization organization1= new Organization();
        organization1.setName("EXPO CENTER");
        
        organization1 = organizationRepository.save(organization1);
        
        log.info("Preloading " + organization1);
        
        Person person1 = new Person();
        person1.setName("Spunge Bob");
        person1.setOrganization(organization1);
        
        person1 = personRepository.save(person1);
        
        Person person2 = new Person();
        person2.setName("Patrick");
        person2.setOrganization(organization1);
        
        person2 = personRepository.save(person2);
        
        log.info("Preloading " + person1);
        log.info("Preloading " + person2);*/
        
        return args -> {
            log.info("Preloading done.");
        };
    }

}
