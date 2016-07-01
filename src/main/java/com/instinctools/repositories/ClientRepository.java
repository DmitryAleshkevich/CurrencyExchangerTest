package com.instinctools.repositories;

import com.instinctools.domain.Client;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by aldm on 1.7.16.
 */
public interface ClientRepository extends GraphRepository<Client> {
}
