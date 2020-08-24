package com.prodyna.pac.pacbackend.load;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.prodyna.pac.pacbackend.model.Event;
import com.prodyna.pac.pacbackend.model.Language;
import com.prodyna.pac.pacbackend.model.Level;
import com.prodyna.pac.pacbackend.model.Location;
import com.prodyna.pac.pacbackend.model.Organization;
import com.prodyna.pac.pacbackend.model.Person;
import com.prodyna.pac.pacbackend.model.Room;
import com.prodyna.pac.pacbackend.model.Talk;
import com.prodyna.pac.pacbackend.model.Topic;
import com.prodyna.pac.pacbackend.repository.EventRepository;
import com.prodyna.pac.pacbackend.repository.LanguageRepository;
import com.prodyna.pac.pacbackend.repository.LevelRepository;
import com.prodyna.pac.pacbackend.repository.LocationRepository;
import com.prodyna.pac.pacbackend.repository.OrganizationRepository;
import com.prodyna.pac.pacbackend.repository.PersonRepository;
import com.prodyna.pac.pacbackend.repository.RoomRepository;
import com.prodyna.pac.pacbackend.repository.TalkRepository;
import com.prodyna.pac.pacbackend.repository.TopicRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class InitialLoad {

    @Bean
    CommandLineRunner initDatabase(final LocationRepository locationRepository, final EventRepository eventRepository, 
                                   final OrganizationRepository organizationRepository, final PersonRepository personRepository, 
                                   final LanguageRepository languageRepository, final LevelRepository levelRepository, 
                                   final RoomRepository roomRepository, final TopicRepository topicRepository, 
                                   final TalkRepository talkRepository) {

        /* LOADING LOCATIONS */

        Location location1 = locationRepository.save(new Location("Belgrade"));
        Location location2 = locationRepository.save(new Location("Frankfurt"));

        log.info("Preloading " + location1);
        log.info("Preloading " + location2);

        /* LOADING EVENTS */

        LocalDateTime actualDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = actualDateTime;
        LocalDateTime secondDayDateTime = actualDateTime.plusDays(1);
        LocalDateTime endDateTime = actualDateTime.plusDays(2);

        Date startDate = Date.from(startDateTime.atZone(ZoneId.of("UTC")).toInstant());
        Date endDate = Date.from(endDateTime.atZone(ZoneId.of("UTC")).toInstant());

        Event event1 = new Event();
        event1.setName("KubeCon");
        event1.setStartDate(startDate);
        event1.setEndDate(endDate);
        event1.setLocation(location1);

        event1 = eventRepository.save(event1);

        Event event2 = new Event();
        event2.setName("Heapcon");
        event2.setStartDate(new Date());
        event2.setEndDate(new Date());
        event2.setLocation(location2);

        event2 = eventRepository.save(event2);

        log.info("Preloading " + event1);
        log.info("Preloading " + event2);

        /* LOADING ORGANIZATIONS */

        Organization organization1 = new Organization();
        organization1.setName("EXPO CENTER");

        organization1 = organizationRepository.save(organization1);

        log.info("Preloading " + organization1);

        /* LOADING PEOPLE */

        Person person1 = new Person();
        person1.setName("Sponge Bob");
        person1.setOrganization(organization1);

        person1 = personRepository.save(person1);

        Person person2 = new Person();
        person2.setName("Patrick");
        person2.setOrganization(organization1);

        person2 = personRepository.save(person2);

        log.info("Preloading " + person1);
        log.info("Preloading " + person2);

        /* LOADING LANGUAGES */

        Language language1 = new Language();
        language1.setName("ENG");

        language1 = languageRepository.save(language1);

        Language language2 = new Language();
        language2.setName("DE");

        language2 = languageRepository.save(language2);

        log.info("Preloading " + language1);
        log.info("Preloading " + language2);

        /* LOADING LEVELS */

        Level level1 = new Level();
        level1.setName("Beginner");

        level1 = levelRepository.save(level1);

        Level level2 = new Level();
        level2.setName("Advanced");

        level2 = levelRepository.save(level2);

        Level level3 = new Level();
        level3.setName("Expert");

        level3 = levelRepository.save(level3);

        log.info("Preloading " + level1);
        log.info("Preloading " + level2);
        log.info("Preloading " + level3);

        /* LOADING ROOMS */

        Room room1 = new Room();
        room1.setName("GREEN ROOM");
        room1.setOrganization(organization1);
        room1.setLocation(location1);

        room1 = roomRepository.save(room1);

        Room room2 = new Room();
        room2.setName("YELLOW ROOM");
        room2.setOrganization(organization1);
        room2.setLocation(location1);

        room2 = roomRepository.save(room2);

        Room room3 = new Room();
        room3.setName("PURPLE ROOM");
        room3.setOrganization(organization1);
        room3.setLocation(location2);

        room3 = roomRepository.save(room3);

        Room room4 = new Room();
        room4.setName("RED ROOM");
        room4.setOrganization(organization1);
        room4.setLocation(location2);

        room4 = roomRepository.save(room4);

        log.info("Preloading " + room1);
        log.info("Preloading " + room1);
        log.info("Preloading " + room3);
        log.info("Preloading " + room4);

        /* LOADING TOPICS */

        Topic topic1 = new Topic();
        topic1.setName("Helm charts");

        topic1 = topicRepository.save(topic1);

        Topic topic2 = new Topic();
        topic2.setName("Terraform");

        topic2 = topicRepository.save(topic2);

        Topic topic3 = new Topic();
        topic3.setName("CNCF");

        Set<Topic> topicSet = new HashSet<>();
        topicSet.add(topic1);
        topicSet.add(topic2);

        topic3.setChildren(topicSet);

        topic3 = topicRepository.save(topic3);

        Topic topic4 = new Topic();
        topic4.setName("Cloud providers");

        topic4 = topicRepository.save(topic4);

        log.info("Preloading " + topic1);
        log.info("Preloading " + topic2);
        log.info("Preloading " + topic3);
        log.info("Preloading " + topic4);

        /* LOADING TALKS */

        LocalDateTime oneHourDateTime = actualDateTime.plusHours(1);
        LocalDateTime twoHourDateTime = actualDateTime.plusHours(2);
        LocalDateTime threeHourDateTime = actualDateTime.plusHours(3);
        LocalDateTime secondDayOneHourDateTime = secondDayDateTime.plusHours(1);
        LocalDateTime secondDayTwoHourDateTime = secondDayDateTime.plusHours(2);
        LocalDateTime secondDayThreeHourDateTime = secondDayDateTime.plusHours(3);
        LocalDateTime secondDayFourHourDateTime = secondDayDateTime.plusHours(4);

        Date oneHourDate = Date.from(oneHourDateTime.atZone(ZoneId.of("UTC")).toInstant());
        Date twoHourDate = Date.from(twoHourDateTime.atZone(ZoneId.of("UTC")).toInstant());
        Date threeHourDate = Date.from(threeHourDateTime.atZone(ZoneId.of("UTC")).toInstant());
        Date secondDayOneHourDate = Date.from(secondDayOneHourDateTime.atZone(ZoneId.of("UTC")).toInstant());
        Date secondDayTwoHourDate = Date.from(secondDayTwoHourDateTime.atZone(ZoneId.of("UTC")).toInstant());
        Date secondDayThreeHourDate = Date.from(secondDayThreeHourDateTime.atZone(ZoneId.of("UTC")).toInstant());
        Date secondDayFourHourDate = Date.from(secondDayFourHourDateTime.atZone(ZoneId.of("UTC")).toInstant());

        Set<Person> personSet1 = new HashSet<>();
        personSet1.add(person1);

        Set<Person> personSet2 = new HashSet<>();
        personSet2.add(person2);

        Set<Person> personSet3 = new HashSet<>();
        personSet3.add(person1);
        personSet3.add(person2);

        Set<Topic> topicSet1 = new HashSet<>();
        topicSet1.add(topic1);
        topicSet1.add(topic2);

        Set<Topic> topicSet2 = new HashSet<>();
        topicSet2.add(topic3);

        Set<Topic> topicSet3 = new HashSet<>();
        topicSet3.add(topic4);

        Talk talk1 = new Talk();
        talk1.setTitle("Kubernetes - Deploy Example Miscroservice");
        talk1.setStartDate(startDate);
        talk1.setEndDate(oneHourDate);
        talk1.setLanguage(language1);
        talk1.setLevel(level2);
        talk1.setEvent(event1);
        talk1.setPersons(personSet1);
        talk1.setRoom(room2);
        talk1.setTopics(topicSet1);

        talk1 = talkRepository.save(talk1);

        Talk talk2 = new Talk();
        talk2.setTitle("Kubernetes - Healthchecks in Kubernetes");
        talk2.setStartDate(twoHourDate);
        talk2.setEndDate(threeHourDate);
        talk2.setLanguage(language1);
        talk2.setLevel(level3);
        talk2.setEvent(event1);
        talk2.setPersons(personSet3);
        talk2.setRoom(room1);
        talk2.setTopics(topicSet2);

        talk2 = talkRepository.save(talk2);

        Talk talk3 = new Talk();
        talk3.setTitle("Deploy and Manage Cloud Environments with Google Cloud");
        talk3.setStartDate(secondDayOneHourDate);
        talk3.setEndDate(secondDayTwoHourDate);
        talk3.setLanguage(language1);
        talk3.setLevel(level1);
        talk3.setEvent(event2);
        talk3.setPersons(personSet2);
        talk3.setRoom(room3);
        talk3.setTopics(topicSet1);

        talk3 = talkRepository.save(talk3);

        Talk talk4 = new Talk();
        talk4.setTitle("Cloud Architecture - Design, Implement, and Manage");
        talk4.setStartDate(secondDayThreeHourDate);
        talk4.setEndDate(secondDayFourHourDate);
        talk4.setLanguage(language1);
        talk4.setLevel(level2);
        talk4.setEvent(event2);
        talk4.setPersons(personSet1);
        talk4.setRoom(room4);
        talk4.setTopics(topicSet3);

        talk4 = talkRepository.save(talk4);

        log.info("Preloading " + talk1);
        log.info("Preloading " + talk2);
        log.info("Preloading " + talk3);
        log.info("Preloading " + talk4);

        return args -> {
            log.info("Preloading done.");
        };

    }

}
