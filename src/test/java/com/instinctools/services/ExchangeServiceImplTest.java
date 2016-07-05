package com.instinctools.services;

import com.instinctools.configs.CurrencyExchangerApplication;
import com.instinctools.configs.SecurityConfig;
import com.instinctools.domain.Client;
import com.instinctools.domain.Currency;
import com.instinctools.domain.Need;
import com.instinctools.repositories.ClientRepository;
import com.instinctools.repositories.CurrencyRepository;
import com.instinctools.repositories.NeedRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by aldm on 1.7.16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CurrencyExchangerApplication.class,SecurityConfig.class})
@WebAppConfiguration
public class ExchangeServiceImplTest {

    @Autowired
    private ExchangeService service;

    @Autowired
    private NeedRepository needRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void getCurrencies() throws Exception {
        final Set<Currency> currencies = service.getCurrencies();
        Currency usd = new Currency("USD", 0);
        Currency euro = new Currency("EUR", 0);
        assertTrue(currencies.contains(usd));
        assertTrue(currencies.contains(euro));
    }

    @Test
    public void storeNeed() throws Exception {
        final String content = "Test";
        final Currency wantedCurrency = new Currency("USD",10);
        currencyRepository.save(wantedCurrency);
        final Currency proposedCurrency = new Currency("EUR",8);
        currencyRepository.save(proposedCurrency);
        Set<Currency> wanted = new HashSet<>();
        wanted.add(wantedCurrency);
        Set<Currency> proposed = new HashSet<>();
        proposed.add(proposedCurrency);
        service.storeNeed(content,wanted,proposed);
        final Need testNeed = needRepository.findOneByContent(content);
        assertNotNull(testNeed);
        needRepository.delete(testNeed);
        currencyRepository.delete(wantedCurrency);
        currencyRepository.delete(proposedCurrency);
    }

    @Test
    public void storeDeals() throws Exception {

    }

    @Test
    @WithMockUser
    public void findDeals() throws Exception {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Client testClient = new Client("user","password");
        final String content = "Test";
        final Currency wantedCurrency = new Currency("USD",10);
        currencyRepository.save(wantedCurrency);
        final Currency proposedCurrency = new Currency("EUR",8);
        currencyRepository.save(proposedCurrency);
        Set<Currency> wanted = new HashSet<>();
        wanted.add(wantedCurrency);
        Set<Currency> proposed = new HashSet<>();
        proposed.add(proposedCurrency);
        Need testNeed = new Need(content,wanted,proposed);
        needRepository.save(testNeed);
        Set<Need> needs = new HashSet<>();
        needs.add(testNeed);
        testClient.setNeedsSet(needs);
        clientRepository.save(testClient);

        Client testClient2 = new Client("user2","password2");
        Need testNeed2 = new Need(content + "1", proposed, wanted);
        needRepository.save(testNeed2);
        Set<Need> needs2 = new HashSet<>();
        needs2.add(testNeed2);
        testClient2.setNeedsSet(needs2);
        clientRepository.save(testClient2);

        final Client testedClient = clientRepository.findByLoginAndPassword(authentication.getName(),authentication.getCredentials().toString());

        final Set<Need> testedNeeds = testedClient.getNeedsSet();
        Set<Need> foundedNeeds = new HashSet<>();

        testedNeeds.forEach(it->{
            Set<Long> wantedIDs = it.getWantedCurrencySet().stream().map(x->x.getIsoCode()).collect(Collectors.toSet());
            Set<Long> proposedIDs = it.getProposedCurrencySet().stream().map(x->x.getIsoCode()).collect(Collectors.toSet());
            foundedNeeds.addAll(needRepository.findAvailableNeeds(wantedIDs,proposedIDs));
        });

        currencyRepository.delete(wantedCurrency);
        currencyRepository.delete(proposedCurrency);
        needRepository.delete(testNeed);
        clientRepository.delete(testClient);
        needRepository.delete(testNeed2);
        clientRepository.delete(testClient2);

        assertEquals(testClient,testedClient);
        assertTrue(foundedNeeds.contains(testNeed2));
    }

}