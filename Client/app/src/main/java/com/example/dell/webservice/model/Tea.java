package com.example.dell.webservice.model;

import java.io.Serializable;
import java.util.List;

public class Tea implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String price;
    private List<Store> storeList;
    private List<Milktea> milkteaList;

    public Tea() {
    }

    public Tea(String id) {
        this.id = id;
    }

    public Tea(String id, String name, String price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<Store> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<Store> storeList) {
        this.storeList = storeList;
    }

    public List<Milktea> getMilkteaList() {
        return milkteaList;
    }

    public void setMilkteaList(List<Milktea> milkteaList) {
        this.milkteaList = milkteaList;
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
        if (!(object instanceof Tea)) {
            return false;
        }
        Tea other = (Tea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tea{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", storeList=" + storeList +
                ", milkteaList=" + milkteaList +
                '}';
    }
}