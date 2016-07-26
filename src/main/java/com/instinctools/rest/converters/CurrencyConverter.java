package com.instinctools.rest.converters;

import com.instinctools.domain.Currency;
import com.instinctools.rest.dto.CurrencyDTO;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class CurrencyConverter {

    protected CurrencyConverter() {
    }

    public static Set<Currency> convert(CurrencyDTO[] dto) {
        Set<Currency> currencies = new ConcurrentSkipListSet<>();
        for (CurrencyDTO currencyDTO : dto) {
            Currency currency = new Currency();
            currency.setName(currencyDTO.getName());
            currency.setSum(0);
            currencies.add(currency);
        }
        return currencies;
    }
}
