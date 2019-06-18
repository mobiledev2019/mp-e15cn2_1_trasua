package com.chaudq.milktea.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "store")
@XmlRootElement

public class Store implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private String id;
    @Column(name = "Name")
    private String name;
    @JoinTable(name = "store_topping", joinColumns = {
            @JoinColumn(name = "StoreId", referencedColumnName = "Id")}, inverseJoinColumns = {
            @JoinColumn(name = "ToppingId", referencedColumnName = "Id")})
    @ManyToMany
    private List<Topping> toppingList;
    @JoinTable(name = "store_tea", joinColumns = {
            @JoinColumn(name = "StoreId", referencedColumnName = "Id")}, inverseJoinColumns = {
            @JoinColumn(name = "TeaId", referencedColumnName = "Id")})
    @ManyToMany
    private List<Tea> teaList;
    @JoinTable(name = "listfavorite_store", joinColumns = {
            @JoinColumn(name = "StoreId", referencedColumnName = "Id")}, inverseJoinColumns = {
            @JoinColumn(name = "ListFavoriteId", referencedColumnName = "Id")})
    @ManyToMany
    private List<Listfavorite> listfavoriteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storeId")
    private List<Address> addressList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storeId")
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

    @XmlTransient
    public List<Topping> getToppingList() {
        return toppingList;
    }

    public void setToppingList(List<Topping> toppingList) {
        this.toppingList = toppingList;
    }

    @XmlTransient
    public List<Tea> getTeaList() {
        return teaList;
    }

    public void setTeaList(List<Tea> teaList) {
        this.teaList = teaList;
    }

    @XmlTransient
    public List<Listfavorite> getListfavoriteList() {
        return listfavoriteList;
    }

    public void setListfavoriteList(List<Listfavorite> listfavoriteList) {
        this.listfavoriteList = listfavoriteList;
    }

    @XmlTransient
    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    @XmlTransient
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