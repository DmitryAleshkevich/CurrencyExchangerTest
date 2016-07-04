package com.instinctools.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

/**
 * Created by aldm on 1.7.16.
 */

@NodeEntity
public class Client {

    @GraphId
    private Long id;
    private String login;
    private String password;

    @Relationship(type="own")
    private Set<Currency> ownCurrencySet;

    @Relationship(type="need")
    private Set<Need> needsSet;

    public Client(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Client() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Need> getNeedsSet() {
        return needsSet;
    }

    public void setNeedsSet(Set<Need> needsSet) {
        this.needsSet = needsSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        return id.equals(client.id);
    }

    @Override
    public int hashCode() {
        return id == null ? System.identityHashCode(this) : id.hashCode();
    }
}
