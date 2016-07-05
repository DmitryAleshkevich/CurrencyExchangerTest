package com.instinctools.utils;

import com.instinctools.domain.Currency;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by aldm on 1.7.16.
 */
@Component
public class CurrencyProviderImpl implements CurrencyProvider{
    @Override
    public Set<Currency> getCurrencies() {
        Currency usd = new Currency("USD", 0);
        Currency euro = new Currency("EUR", 0);
        Set<Currency> currencies = new HashSet<>();
        currencies.add(usd);
        currencies.add(euro);
        return currencies;
    }
}
