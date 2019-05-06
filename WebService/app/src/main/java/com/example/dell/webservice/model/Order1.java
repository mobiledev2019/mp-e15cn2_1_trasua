package com.example.dell.webservice.model;

import java.io.Serializable;
import java.util.List;



public class Order1 implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String date;
    private List<Milktea> milkteaList;
    private List<Bill> billList;
    private List<Statusorder> statusorderList;
    private Addressship addressShipId;
    private Payment paymentId;
    private User user;

    public Order1() {
    }

    public Order1(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public List<Milktea> getMilkteaList() {
        return milkteaList;
    }

    public void setMilkteaList(List<Milktea> milkteaList) {
        this.milkteaList = milkteaList;
    }

    public List<Bill> getBillList() {
        return billList;
    }

    public void setBillList(List<Bill> billList) {
        this.billList = billList;
    }

    public List<Statusorder> getStatusorderList() {
        return statusorderList;
    }

    public void setStatusorderList(List<Statusorder> statusorderList) {
        this.statusorderList = statusorderList;
    }

    public Addressship getAddressShipId() {
        return addressShipId;
    }

    public void setAddressShipId(Addressship addressShipId) {
        this.addressShipId = addressShipId;
    }

    public Payment getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Payment paymentId) {
        this.paymentId = paymentId;
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
        if (!(object instanceof Order1)) {
            return false;
        }
        Order1 other = (Order1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication23.Order1[ id=" + id + " ]";
    }

}
