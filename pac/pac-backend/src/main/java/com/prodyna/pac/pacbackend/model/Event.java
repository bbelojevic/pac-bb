package com.prodyna.pac.pacbackend.model;

import java.util.Date;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NodeEntity
@Data
@Getter
@Setter
@NoArgsConstructor
public class Event /*extends RepresentationModel<Event>*/ {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Date startDate;

    private Date endDate;

    @Relationship(type = "IN_LOCATION")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Location location;

    @JsonIgnore
    @Relationship(type = "ON_EVENT", direction = Relationship.INCOMING)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Talk> talks;

}
