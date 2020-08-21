package com.prodyna.pac.pacbackend.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.prodyna.pac.pacbackend.model.Organization;

import io.micrometer.core.annotation.Timed;

@Timed
public interface OrganizationRepository extends Neo4jRepository<Organization, Long> {

}
