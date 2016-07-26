package com.instinctools.rest.dto;

import java.util.Set;

public class CurrenciesDTO {

    private Set<CurrencyDTO> currencyDTOs;

    public CurrenciesDTO() {
    }

    public Set<CurrencyDTO> getCurrencyDTOs() {
        return currencyDTOs;
    }

    public void setCurrencyDTOs(Set<CurrencyDTO> currencyDTOs) {
        this.currencyDTOs = currencyDTOs;
    }

}
