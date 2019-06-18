package com.chaudq.milktea.model;

import java.io.Serializable;

public class Registrator implements Serializable {
    User user;
    String id;

    @Override
    public String toString() {
        return "Registrator{" +
                "user=" + user +
                ", id='" + id + '\'' +
                '}';
    }

    public Registrator() {
    }

    public Registrator(User user, String id) {
        this.user = user;
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
