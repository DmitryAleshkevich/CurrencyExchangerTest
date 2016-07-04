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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Participant that = (Participant) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? System.identityHashCode(this) : id.hashCode();
    }
}
