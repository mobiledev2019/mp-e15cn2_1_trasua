package com.example.dell.webservice.model;

import java.io.Serializable;
import java.util.List;


public class Topping implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String price;
    private List<Milktea> milkteaList;
    private List<Store> storeList;

    public Topping(String id, String name, String price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Topping() {
    }

    public Topping(String id) {
        this.id = id;
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

    public List<Milktea> getMilkteaList() {
        return milkteaList;
    }

    public void setMilkteaList(List<Milktea> milkteaList) {
        this.milkteaList = milkteaList;
    }

    public List<Store> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<Store> storeList) {
        this.storeList = storeList;
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
        if (!(object instanceof Topping)) {
            return false;
        }
        Topping other = (Topping) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Topping{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", milkteaList=" + milkteaList +
                ", storeList=" + storeList +
                '}';
    }
}