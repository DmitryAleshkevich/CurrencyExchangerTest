package com.instinctools.services;

import com.instinctools.domain.Currency;
import com.instinctools.domain.Deal;

import java.util.Set;

/**
 * Created by aldm on 1.7.16.
 */
public interface ExchangeService {
    Set<Currency> getCurrencies();
    Set<Deal> findDeals(double deltaNeed, double deltaProposed);
    void storeDeals(Set<Deal> deals);
    void storeNeed(String content, Set<Currency> wantedCurrencySet, Set<Currency> proposedCurrencySet);
}
