package com.prodyna.pac.pacbackend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prodyna.pac.pacbackend.dto.EventDto;
import com.prodyna.pac.pacbackend.model.Event;
import com.prodyna.pac.pacbackend.model.Topic;
import com.prodyna.pac.pacbackend.repository.EventRepository;

import io.micrometer.core.annotation.Timed;

@RepositoryRestController
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Timed("pac.events.getallwithtopics")
    @RequestMapping(method = RequestMethod.GET, value = "/events/search/getAllEventsWithTopics")
    public ResponseEntity<Iterable<EventDto>> getAllEventsWithTopics() {

        List<EventDto> eventsWithTopics = new ArrayList<>();

        Iterable<Event> events = eventRepository.findAll();

        for (Event event : events) {
            Long eventId = event.getId();

            //event.add(linkTo(Event.class).slash("events").slash(eventId).withSelfRel());

            Set<Topic> topics = eventRepository.findTopicsForEvent(eventId);

            eventsWithTopics.add(new EventDto(event, topics));
        }

        return ResponseEntity.ok(eventsWithTopics);

    }

}
