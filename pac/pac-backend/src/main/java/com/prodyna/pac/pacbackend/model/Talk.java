package com.prodyna.pac.pacbackend.model;

import java.util.Date;
import java.util.Set;

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
public class Talk {
    
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    
    private Date startDate;

    private Date endDate;
    
    @Relationship(type="ON_LANGUAGE")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Language language;

    @Relationship(type="IS_LEVEL")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Level level;
    
    @Relationship(type = "BY_PERSON")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Person> persons;

    @Relationship(type="IS_USED")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Topic> topics;
    
    @Relationship(type="IN_ROOM")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Room room;
    
    @Relationship(type="ON_EVENT")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Event event;
    
}
