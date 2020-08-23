package com.prodyna.pac.pacbackend.projection;

import java.util.Date;
import java.util.Set;

import org.springframework.data.rest.core.config.Projection;

import com.prodyna.pac.pacbackend.model.Event;
import com.prodyna.pac.pacbackend.model.Language;
import com.prodyna.pac.pacbackend.model.Level;
import com.prodyna.pac.pacbackend.model.Person;
import com.prodyna.pac.pacbackend.model.Talk;
import com.prodyna.pac.pacbackend.model.Topic;

@Projection(name = "inlineTalk", types = { Talk.class })
public interface TalkProjection {

    String getTitle();

    Date getStartDate();

    Date getEndDate();

    Level getLevel();

    Language getLanguage();

    Set<Person> getPersons();

    Set<Topic> getTopics();

    Event getEvent();

}
