package com.prodyna.pac.pacbackend.model;

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
public class Topic {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type = "IS_RELATED_TO", direction = Relationship.OUTGOING)
    private Set<Topic> parents;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type = "IS_RELATED_TO", direction = Relationship.INCOMING)
    private Set<Topic> children;

    @Relationship(type = "IS_USED", direction = Relationship.INCOMING)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Talk> talks;

}
