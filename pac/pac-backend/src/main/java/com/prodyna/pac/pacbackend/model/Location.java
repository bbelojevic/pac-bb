package com.prodyna.pac.pacbackend.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NodeEntity
@Data
@Getter 
@Setter 
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Location(String name) {
        super();
        this.name = name;
    }

}
