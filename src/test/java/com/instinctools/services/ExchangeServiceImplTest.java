package com.instinctools.services;

import com.instinctools.configs.CurrencyExchangerApplication;
import com.instinctools.configs.SecurityConfig;
import com.instinctools.domain.*;
import com.instinctools.repositories.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
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

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    private Set<Currency> currencies;
    private Currency usd0 = new Currency("USD", 0);
    private Currency euro0 = new Currency("EUR", 0);
    private String content = "Test";
    private String content2 = "Test2";
    private final Currency wantedCurrency = new Currency("USD",10);
    private final Currency proposedCurrency = new Currency("EUR",8);
    private Set<Currency> wanted = new HashSet<>();
    private Set<Currency> proposed = new HashSet<>();
    private Client testClient = new Client("user","password");
    private Client testClient2 = new Client("user2","password2");
    private Need testNeed;
    private Need testNeed2;
    private boolean founded = false;

    @Before
    public void setUp() throws Exception {
        currencies = service.getCurrencies();
        currencyRepository.save(wantedCurrency);
        currencyRepository.save(proposedCurrency);
        wanted.add(wantedCurrency);
        proposed.add(proposedCurrency);
        testNeed = new Need(content,wanted,proposed);
        needRepository.save(testNeed);
        Set<Need> needs = new HashSet<>();
        needs.add(testNeed);
        testClient.setNeedsSet(needs);
        clientRepository.save(testClient);
        testNeed2 = new Need(content + "1", proposed, wanted);
        needRepository.save(testNeed2);
        Set<Need> needs2 = new HashSet<>();
        needs2.add(testNeed2);
        testClient2.setNeedsSet(needs2);
        clientRepository.save(testClient2);

    }

    @Test
    public void getCurrencies() throws Exception {
        assertTrue(currencies.contains(usd0));
        assertTrue(currencies.contains(euro0));
    }

    @Test
    public void storeNeed() throws Exception {
        service.storeNeed(content2,wanted,proposed);
        final Need need = needRepository.findOneByContent(content2);
        needRepository.delete(need);
        assertNotNull(need);
    }

    @Test
    public void storeDeals() throws Exception {

    }

    @Test
    @WithMockUser
    public void findDeals() throws Exception {
        Set<Participant> participants = service.findDeals();
        participants.forEach(it->{
            if (it.getNeed().equals(testNeed2)) {
                founded = true;
                dealRepository.delete(it.getDeal());
                participantRepository.delete(it);
            }
        });
        assertTrue(founded);
    }

    @After
    public void setDown() throws Exception {
        currencyRepository.delete(wantedCurrency);
        currencyRepository.delete(proposedCurrency);
        needRepository.delete(testNeed);
        clientRepository.delete(testClient);
        needRepository.delete(testNeed2);
        clientRepository.delete(testClient2);
    }

}