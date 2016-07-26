package com.instinctools.utils;

import com.instinctools.domain.Currency;
import com.instinctools.rest.converters.CurrencyConverter;
import com.instinctools.rest.dto.CurrencyDTO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.ref.SoftReference;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Component
public class CurrencyProviderImpl implements CurrencyProvider{

    private SoftReference<Set<Currency>> currenciesSoftRef;
    private static final Logger logger = LogManager.getLogger(CurrencyProviderImpl.class);
    private RestTemplate restTemplate = new RestTemplate();
    private static final String URL = "http://www.nbrb.by/API/ExRates/Currencies";

    @Override
    public Set<Currency> getCurrencies() {
        if (currenciesSoftRef == null) {
            Future<CurrencyDTO[]> result = downloadCurrencies();
            while (!result.isDone()) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                    break;
                }
            }
            try {
                final CurrencyDTO[] currenciesDownloaded = result.get();
                Set<Currency> currenciesConverted = CurrencyConverter.convert(currenciesDownloaded);
                currenciesSoftRef = new SoftReference<>(currenciesConverted);
                return currenciesConverted;
            } catch (Exception e) {
                logger.error(e.getMessage());
                return new ConcurrentSkipListSet<>();
            }
        }
        return currenciesSoftRef.get();
    }

    @Async
    private Future<CurrencyDTO[]> downloadCurrencies() {
        CurrencyDTO[] currencies = restTemplate.getForObject(URL,CurrencyDTO[].class);
        return new AsyncResult<>(currencies);
    }

}
