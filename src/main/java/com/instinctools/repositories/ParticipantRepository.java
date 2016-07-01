package com.instinctools.repositories;

import com.instinctools.domain.Participant;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by aldm on 1.7.16.
 */
public interface ParticipantRepository extends GraphRepository<Participant>{
}
