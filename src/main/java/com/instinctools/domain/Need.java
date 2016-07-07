package com.instinctools.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

/**
 * Created by aldm on 1.7.16.
 */

@NodeEntity
public class Need {

    @GraphId
    private Long id;
    private String content;

    private Client client;

    @Relationship(type="wanted")
    private Set<Currency> wantedCurrencySet;

    @Relationship(type="proposed")
    private Set<Currency> proposedCurrencySet;

    public Need(String content, Client client, Set<Currency> wantedCurrencySet, Set<Currency> proposedCurrencySet) {
        this.content = content;
        this.client = client;
        this.wantedCurrencySet = wantedCurrencySet;
        this.proposedCurrencySet = proposedCurrencySet;
    }

    public Need() {}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public Set<Currency> getWantedCurrencySet() {
        return wantedCurrencySet;
    }

    public Set<Currency> getProposedCurrencySet() {
        return proposedCurrencySet;
    }

}
