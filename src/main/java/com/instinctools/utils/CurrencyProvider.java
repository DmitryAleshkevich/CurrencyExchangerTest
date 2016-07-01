package com.instinctools.utils;

import com.instinctools.domain.Currency;

import java.util.Set;

/**
 * Created by aldm on 1.7.16.
 */
public interface CurrencyProvider {
    Set<Currency> getCurrencies();
}
