package com.instinctools.services;

import com.instinctools.domain.*;
import com.instinctools.repositories.ClientRepository;
import com.instinctools.repositories.DealRepository;
import com.instinctools.repositories.NeedRepository;
import com.instinctools.repositories.ParticipantRepository;
import com.instinctools.utils.CurrencyProvider;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by aldm on 1.7.16.
 */

@Service
@Transactional
public class ExchangeServiceImpl implements ExchangeService {

    private static final Logger logger = LogManager.getLogger(ExchangeServiceImpl.class);

    @Autowired
    private CurrencyProvider currencyProvider;

    @Autowired
    private NeedRepository needRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Override
    public Set<Currency> getCurrencies() {
        return currencyProvider.getCurrencies();
    }

    @Override
    public void storeNeed(String content, Set<Currency> wantedCurrencySet, Set<Currency> proposedCurrencySet) {
        needRepository.save(new Need(content,wantedCurrencySet,proposedCurrencySet));
    }

    @Override
    public void putMoney(Currency currency) {
        final Client client = getClientByCredentials();
        HashSet<Currency> currencies = new HashSet<>();
        currencies.add(currency);
        client.setOwnCurrencySet(currencies);
        clientRepository.save(client);
    }

    @Override
    public void storeDeals(Set<Participant> participants) {
        participants.forEach(it->{
            it.setAgreed(true);
            it.getDeal().setDateAccepted(new Date());
        });
    }

    @Override
    public Set<Participant> findDeals() {
        final Client client = getClientByCredentials();
        final Set<Need> needs = client.getNeedsSet();
        if (needs.isEmpty()) {
            logger.info("Client " + client.getLogin() + " has no needs!");
        }
        final Set<Need> foundedNeeds = findNeeds(needs);
        if (foundedNeeds.isEmpty()) {
            logger.info("No possible deals found for " + client.getLogin() + " needs!");
        }
        return producePossibleDeals(foundedNeeds);
    }

    private Set<Participant> producePossibleDeals(Set<Need> foundedNeeds) {
        Set<Participant> producedDeals = new HashSet<>();
        foundedNeeds.forEach(it->{
            Deal deal = new Deal(new Date());
            dealRepository.save(deal);
            Participant participant = new Participant();
            participant.setNeed(it);
            participant.setDeal(deal);
            participantRepository.save(participant);
            producedDeals.add(participant);
        });

        return producedDeals;
    }

    private Set<Need> findNeeds(Set<Need> needs) {
        Set<Need> foundedNeeds = new HashSet<>();
        needs.forEach(it->{
            Set<Long> wantedIDs = it.getWantedCurrencySet().stream().map(x->x.getId()).collect(Collectors.toSet());
            Set<Long> proposedIDs = it.getProposedCurrencySet().stream().map(x->x.getId()).collect(Collectors.toSet());
            foundedNeeds.addAll(needRepository.findAvailableNeeds(wantedIDs,proposedIDs));
        });
        return foundedNeeds;
    }

    private Client getClientByCredentials() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return clientRepository.findByLoginAndPassword(authentication.getName(),authentication.getCredentials().toString());
    }

}
