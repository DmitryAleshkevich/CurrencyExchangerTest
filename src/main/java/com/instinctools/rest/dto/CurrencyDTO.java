package com.instinctools.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyDTO {

    @JsonProperty(value = "Cur_Abbreviation")
    private String name;

    public CurrencyDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
