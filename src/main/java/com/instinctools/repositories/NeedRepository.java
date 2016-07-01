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

    @Query("MATCH ")
    Set<Need> findAvailableNeeds(@Param("wantedCurrencySet") Set<Currency> wantedCurrencySet, @Param("proposedCurrencySet") Set<Currency> proposedCurrencySet,
                                 @Param("deltaNeed") double deltaNeed, @Param("deltaProposed") double deltaProposed);
}
