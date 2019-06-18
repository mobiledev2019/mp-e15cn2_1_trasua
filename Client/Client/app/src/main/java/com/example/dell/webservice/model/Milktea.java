package com.example.dell.webservice.model;

import com.example.dell.webservice.model.Order1;
import com.example.dell.webservice.model.Tea;

import java.io.Serializable;
import java.util.List;


public class Milktea implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String size;
    private Topping topping;
    private Order1 orderId;
    private Tea teaId;

    public Milktea() {
    }

    public Milktea(String id) {
        this.id = id;
    }

    public Milktea(String id, boolean size) {
        this.id = id;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Topping getTopping() {
        return topping;
    }

    public void setTopping(Topping topping) {
        this.topping = topping;
    }

    public Order1 getOrder1() {
        return orderId;
    }

    public void setOrder1(Order1 order1) {
        this.orderId = order1;
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
        return "Milktea{" +
                "id='" + id + '\'' +
                ", size='" + size + '\'' +
                ", topping=" + topping +
                ", order1List=" + orderId +
                ", teaId=" + teaId +
                '}';
    }
}
