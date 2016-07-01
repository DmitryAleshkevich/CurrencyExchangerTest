package com.instinctools.domain_model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by aldm on 1.7.16.
 */
@NodeEntity
public class Currency {

    @GraphId
    private Long isoCode;
    private String name;
    private double sum;

    public Currency(Long isoCode, String name, double sum) {
        this.isoCode = isoCode;
        this.name = name;
        this.sum = sum;
    }

    public Currency() {}

    public Long getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(Long isoCode) {
        this.isoCode = isoCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Currency currency = (Currency) o;
        return isoCode.equals(currency.isoCode);
    }

    @Override
    public int hashCode() {
        return isoCode.hashCode();
    }
}
