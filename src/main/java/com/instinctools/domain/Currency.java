package com.instinctools.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by aldm on 1.7.16.
 */
@NodeEntity
public class Currency {

    @GraphId
    private Long id;
    private String name;
    private double sum;

    public Currency(String name, double sum) {
        this.name = name;
        this.sum = sum;
    }

    public Currency() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return name.equals(currency.name);
    }

    @Override
    public int hashCode() {
        return name == null ? System.identityHashCode(this) : name.hashCode();
    }
}
