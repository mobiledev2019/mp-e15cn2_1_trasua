package com.example.dell.webservice.model;


import java.io.Serializable;
import java.util.List;

public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private List<Topping> toppingList;
    private List<Tea> teaList;
    private List<Listfavorite> listfavoriteList;
    private List<Address> addressList;
    private List<Feedback> feedbackList;

    public Store(String id, String name, List<Topping> toppingList, List<Tea> teaList, List<Address> addressList) {
        this.id = id;
        this.name = name;
        this.toppingList = toppingList;
        this.teaList = teaList;
        this.addressList = addressList;
    }

    public Store() {
    }

    public Store(String id) {
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

    public List<Topping> getToppingList() {
        return toppingList;
    }

    public void setToppingList(List<Topping> toppingList) {
        this.toppingList = toppingList;
    }

    public List<Tea> getTeaList() {
        return teaList;
    }

    public void setTeaList(List<Tea> teaList) {
        this.teaList = teaList;
    }

    public List<Listfavorite> getListfavoriteList() {
        return listfavoriteList;
    }

    public void setListfavoriteList(List<Listfavorite> listfavoriteList) {
        this.listfavoriteList = listfavoriteList;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
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
        if (!(object instanceof Store)) {
            return false;
        }
        Store other = (Store) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", toppingList=" + toppingList +
                ", teaList=" + teaList +
                ", listfavoriteList=" + listfavoriteList +
                ", addressList=" + addressList +
                ", feedbackList=" + feedbackList +
                '}';
    }
}