package com.instinctools.rest.converters;

import com.instinctools.domain.Currency;
import com.instinctools.rest.dto.CurrenciesDTO;
import com.instinctools.rest.dto.CurrencyDTO;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by aldm on 26.07.16.
 */
public class CurrencyConverter {
    public static Set<Currency> convert(CurrenciesDTO dto) {
        Set<Currency> currencies = new ConcurrentSkipListSet();
        for (CurrencyDTO currencyDTO : dto.getCurrencyDTOs()) {
            Currency currency = new Currency();
            currency.setName(currencyDTO.getName());
            currency.setSum(0);
            currencies.add(currency);
        }
        return currencies;
    }
}
