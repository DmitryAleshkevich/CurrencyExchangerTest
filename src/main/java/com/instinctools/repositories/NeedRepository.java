package com.instinctools.repositories;

import com.instinctools.domain.Currency;
import com.instinctools.domain.Need;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * Created by aldm on 1.7.16.
 */
public interface NeedRepository extends GraphRepository<Need>{

    @Query("MATCH (c2)<-[r2:proposed]-(n)-[r1:wanted]->(c1) WHERE id(c2) IN {0} AND id(c1) IN {1} RETURN COLLECT(DISTINCT n)")
    Set<Need> findAvailableNeeds(Set<Long> wantedCurrencySet, Set<Long> proposedCurrencySet);

    Need findOneByContent(String content);
}