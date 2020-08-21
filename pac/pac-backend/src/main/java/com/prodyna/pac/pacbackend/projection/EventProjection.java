package com.prodyna.pac.pacbackend.projection;

import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import com.prodyna.pac.pacbackend.model.Event;
import com.prodyna.pac.pacbackend.model.Location;

@Projection(name = "inlineEvent", types = { Event.class }) 
public interface EventProjection {

    String getName();

    Date getStartDate();

    Date getEndDate();

    Location getLocation();

}
