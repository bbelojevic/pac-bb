package com.prodyna.pac.pacbackend.dto;

import java.util.Set;

import com.prodyna.pac.pacbackend.model.Event;
import com.prodyna.pac.pacbackend.model.Topic;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class EventDto {

    private Event event;
    private Set<Topic> topics;

    public EventDto(Event event, Set<Topic> topics) {
        this.event = event;
        this.topics = topics;
    }

}
