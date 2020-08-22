package com.prodyna.pac.pacbackend.projection;

import java.util.Set;

import org.springframework.data.rest.core.config.Projection;

import com.prodyna.pac.pacbackend.model.Organization;
import com.prodyna.pac.pacbackend.model.Person;
import com.prodyna.pac.pacbackend.model.Talk;

@Projection(name = "inlinePerson", types = { Person.class })
public interface PersonProjection {

    String getName();

    Organization getOrganization();
    
    Set<Talk> getTalks();

}
