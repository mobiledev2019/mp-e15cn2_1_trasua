package com.example.dell.webservice.model;

import com.example.dell.webservice.model.Order1;
import com.example.dell.webservice.model.Tea;

import java.io.Serializable;
import java.util.List;


public class Milktea implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private boolean size;
    private List<Topping> toppingList;
    private List<Order1> order1List;
    private Tea teaId;

    public Milktea() {
    }

    public Milktea(String id) {
        this.id = id;
    }

    public Milktea(String id, boolean size) {
        this.id = id;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getSize() {
        return size;
    }

    public void setSize(boolean size) {
        this.size = size;
    }

    public List<Topping> getToppingList() {
        return toppingList;
    }

    public void setToppingList(List<Topping> toppingList) {
        this.toppingList = toppingList;
    }

    public List<Order1> getOrder1List() {
        return order1List;
    }

    public void setOrder1List(List<Order1> order1List) {
        this.order1List = order1List;
    }

    public Tea getTeaId() {
        return teaId;
    }

    public void setTeaId(Tea teaId) {
        this.teaId = teaId;
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
        if (!(object instanceof Milktea)) {
            return false;
        }
        Milktea other = (Milktea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication23.Milktea[ id=" + id + " ]";
    }

}
