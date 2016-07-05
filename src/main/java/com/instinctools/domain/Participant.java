package com.instinctools.domain;

import org.neo4j.ogm.annotation.*;

/**
 * Created by aldm on 1.7.16.
 */

@RelationshipEntity(type = "participant")
public class Participant {

    @GraphId
    private Long id;

    @Property
    private boolean agreed;

    @StartNode
    private Need need;

    @EndNode
    private Deal deal;

    public Participant(Long id) {
        this.id = id;
    }

    public Participant() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAgreed() {
        return agreed;
    }

    public void setAgreed(boolean agreed) {
        this.agreed = agreed;
    }

    public Need getNeed() {
        return need;
    }

    public void setNeed(Need need) {
        this.need = need;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

}
