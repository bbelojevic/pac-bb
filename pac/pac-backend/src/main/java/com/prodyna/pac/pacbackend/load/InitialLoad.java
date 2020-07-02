package com.prodyna.pac.pacbackend.load;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.prodyna.pac.pacbackend.model.Location;
import com.prodyna.pac.pacbackend.repository.LocationRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class InitialLoad {

    @Bean
    CommandLineRunner initDatabase(final LocationRepository locationRepository) {
        return args -> {
            log.info("Preloading " + locationRepository.save(new Location("Banana")));
            log.info("Preloading " + locationRepository.save(new Location("Pineapple")));
        };
    }

}
