package com.example.dell.webservice.model;

import android.arch.persistence.room.Entity;

import java.io.Serializable;


/**
 *
 * @author GomComputer
 */
@Entity

public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String longtitude;
    private String latitude;
    private String description;
    private Store storeId;

    public Address(String id, String longtitude, String latitude, String description) {
        this.id = id;
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.description = description;
    }

    public Address() {
    }

    public Address(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Store getStoreId() {
        return storeId;
    }

    public void setStoreId(Store storeId) {
        this.storeId = storeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return description;
    }
}
