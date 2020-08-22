package com.prodyna.pac.pacbackend.projection;

import org.springframework.data.rest.core.config.Projection;

import com.prodyna.pac.pacbackend.model.Organization;
import com.prodyna.pac.pacbackend.model.Person;

@Projection(name = "inlinePerson", types = { Person.class })
public interface PersonProjection {

    String getName();

    Organization getOrganization();

}
