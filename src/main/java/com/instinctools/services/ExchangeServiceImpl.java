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
import java.util.Objects;
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
        final Client client = getClientByCredentials();
        needRepository.save(new Need(content,client,wantedCurrencySet,proposedCurrencySet));
    }

    @Override
    public void putMoney(Currency currency) {
        final Client client = getClientByCredentials();
        Set<Currency> currenciesModified = getNewCurrenciesForClient(currency, client.getOwnCurrencySet());
        client.setOwnCurrencySet(currenciesModified);
        clientRepository.save(client);
    }

    @Override
    public String storeDeals(Set<Participant> participants) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Participant participant: participants) {
            if (!checkBalanceForDeal(participant.getDeal().getClient(),participant.getNeed().getProposedCurrencySet())) {
                stringBuilder.append("You haven`t enough money for deal with id ");
                stringBuilder.append(participant.getDeal().getId());
                stringBuilder.append("!");
                stringBuilder.append("\"\\n\"");
                continue;
            }
            if (!checkBalanceForDeal(participant.getNeed().getClient(),participant.getNeed().getWantedCurrencySet())) {
                stringBuilder.append("Producer haven`t enough money for deal with id ");
                stringBuilder.append(participant.getDeal().getId());
                stringBuilder.append("!");
                stringBuilder.append("\"\\n\"");
                continue;
            }
            participant.setAgreed(true);
            participant.getDeal().setDateAccepted(new Date());
        }
        return stringBuilder.toString().isEmpty() ? "success" : stringBuilder.toString();
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
        return producePossibleDeals(foundedNeeds,client);
    }

    private Set<Participant> producePossibleDeals(Set<Need> foundedNeeds, Client client) {
        Set<Participant> producedDeals = new HashSet<>();
        foundedNeeds.forEach(it->{
            Deal deal = new Deal(new Date(),client);
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
            Set<Long> wantedIDs = it.getWantedCurrencySet().stream().map(Currency::getId).collect(Collectors.toSet());
            Set<Long> proposedIDs = it.getProposedCurrencySet().stream().map(Currency::getId).collect(Collectors.toSet());
            foundedNeeds.addAll(needRepository.findAvailableNeeds(wantedIDs,proposedIDs));
        });
        return foundedNeeds;
    }

    private Client getClientByCredentials() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return clientRepository.findByLoginAndPassword(authentication.getName(),authentication.getCredentials().toString());
    }

    private Set<Currency> getNewCurrenciesForClient(Currency currency, Set<Currency> currencies) {
        boolean founded = false;
        for (Currency c : currencies) {
            if (Objects.equals(c.getName(), currency.getName()))
            {
                c.setSum(currency.getSum() + currency.getSum());
                founded = true;
                break;
            }
        }
        if (!founded) {
            currencies.add(currency);
        }
        return currencies;
    }

    private boolean checkBalanceForDeal(Client client, Set<Currency> currencies) {
        Set<Currency> ownCurrencies = client.getOwnCurrencySet();
        for (Currency currencyOfDeal : currencies) {
            boolean foundMatch = false;
            for (Currency ownCurrency : ownCurrencies) {
                if (Objects.equals(ownCurrency.getName(), currencyOfDeal.getName())) {
                    if (ownCurrency.getSum() < currencyOfDeal.getSum()) {
                        return false;
                    }
                    else {
                        foundMatch = true;
                        break;
                    }
                }
            }
            if (!foundMatch) {
                return false;
            }
        }
        return true;
    }

}
