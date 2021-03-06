package com.instinctools.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.Date;

/**
 * Created by aldm on 1.7.16.
 */

@NodeEntity
public class Deal {

    @GraphId
    private Long id;
    private Date dateProposed;
    private Date dateAccepted;

    private Client client;

    public Deal(Date dateProposed, Client client) {
        this.dateProposed = dateProposed;
        this.client = client;
    }

    public Deal() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateProposed() {
        return dateProposed;
    }

    public void setDateProposed(Date dateProposed) {
        this.dateProposed = dateProposed;
    }

    public Date getDateAccepted() {
        return dateAccepted;
    }

    public Client getClient() {
        return client;
    }

    public void setDateAccepted(Date dateAccepted) {
        this.dateAccepted = dateAccepted;
    }

}
