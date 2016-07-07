package com.instinctools.services;

import com.instinctools.domain.Currency;
import com.instinctools.domain.Deal;
import com.instinctools.domain.Participant;

import java.util.Set;

/**
 * Created by aldm on 1.7.16.
 */
public interface ExchangeService {
    Set<Currency> getCurrencies();
    Set<Participant> findDeals();
    String storeDeals(Set<Participant> deals);
    void storeNeed(String content, Set<Currency> wantedCurrencySet, Set<Currency> proposedCurrencySet);
    void putMoney(Currency currency);
}
