package com.prodyna.pac.pacbackend.model;

import java.util.Date;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

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
public class Event {

    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type="IN_LOCATION")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Location location;

    private String name;

    private Date startDate;

    private Date endDate;
    
}
