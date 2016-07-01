package com.instinctools.services;

import com.instinctools.domain.Client;
import com.instinctools.domain.Currency;
import com.instinctools.domain.Deal;
import com.instinctools.domain.Need;
import com.instinctools.repositories.ClientRepository;
import com.instinctools.repositories.NeedRepository;
import com.instinctools.utils.CurrencyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by aldm on 1.7.16.
 */

@Service
@Transactional
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    private CurrencyProvider currencyProvider;

    @Autowired
    private NeedRepository needRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Set<Currency> getCurrencies() {
        return currencyProvider.getCurrencies();
    }

    @Override
    public void storeNeed(String content, Set<Currency> wantedCurrencySet, Set<Currency> proposedCurrencySet) {
        needRepository.save(new Need(content,wantedCurrencySet,proposedCurrencySet));
    }

    @Override
    public void storeDeals(Set<Deal> deals) {

    }

    @Override
    public Set<Deal> findDeals(double deltaNeed, double deltaProposed) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Client client = clientRepository.findByLoginAndPassword(authentication.getName(),authentication.getCredentials().toString());
        final Set<Need> clientNeeds = client.getNeedsSet();
        return null;
    }

    private Set<Deal> getDealsByNeeds(Set<Need> needs, double deltaNeed, double deltaProposed) {
        needs.forEach(it->{
            Set<Need> needSet = new HashSet<>();
            needSet.addAll(needRepository.findAvailableNeeds(it.getWantedCurrencySet(),it.getProposedCurrencySet(),deltaNeed,deltaProposed));
        });
        return null;
    }
}
