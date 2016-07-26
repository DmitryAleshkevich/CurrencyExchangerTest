package com.instinctools.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldm on 26.07.16.
 */
public class CurrencyDTO {

    @JsonProperty(value = "Cur_Name_Eng")
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
