package com.chaudq.milktea.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "topping")
@XmlRootElement

public class Topping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private String id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Price")
    private String price;
    @JoinTable(name = "milktea_topping", joinColumns = {
            @JoinColumn(name = "ToppingId", referencedColumnName = "Id")}, inverseJoinColumns = {
            @JoinColumn(name = "MilkteaId", referencedColumnName = "Id")})
    @ManyToMany
    private List<Milktea> milkteaList;
    @ManyToMany(mappedBy = "toppingList")
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

    @XmlTransient
    public List<Milktea> getMilkteaList() {
        return milkteaList;
    }

    public void setMilkteaList(List<Milktea> milkteaList) {
        this.milkteaList = milkteaList;
    }

    @XmlTransient
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