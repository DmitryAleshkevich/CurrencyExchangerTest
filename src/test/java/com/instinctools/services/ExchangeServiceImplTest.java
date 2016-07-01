package com.instinctools.services;

import com.instinctools.configs.CurrencyExchangerApplication;
import com.instinctools.configs.SecurityConfig;
import com.instinctools.domain.Currency;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.util.Set;

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

    @Test
    public void getCurrencies() throws Exception {
        Set<Currency> currencies = service.getCurrencies();
        Currency usd = new Currency(new Long(840),"USD", 0);
        Currency euro = new Currency(new Long(978),"EUR", 0);
        assertTrue(currencies.contains(usd));
        assertTrue(currencies.contains(euro));
    }

    @Test
    public void storeNeed() throws Exception {

    }

    @Test
    public void storeDeals() throws Exception {

    }

    @Test
    public void findDeals() throws Exception {

    }

}