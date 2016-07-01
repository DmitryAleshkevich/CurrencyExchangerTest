package com.instinctools.repositories;

import com.instinctools.domain.Currency;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by aldm on 1.7.16.
 */
public interface CurrencyRepository extends GraphRepository<Currency> {
}
