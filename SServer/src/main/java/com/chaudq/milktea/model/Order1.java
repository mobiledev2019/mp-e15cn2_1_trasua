package com.chaudq.milktea.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author GomComputer
 */
@Entity
@Table(name = "order")
@XmlRootElement

public class Order1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private String id;
    @Column(name = "Date")
    private String date;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
    private List<Bill> billList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
    private List<Milktea> milkteaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
    private List<Statusorder> statusorderList;
    @JoinColumn(name = "AddressShipId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Addressship addressShipId;
    @JoinColumn(name = "MemberId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "PaymentId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Payment paymentId;
    private Store store;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

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

    @XmlTransient
    public List<Bill> getBillList() {
        return billList;
    }

    public void setBillList(List<Bill> billList) {
        this.billList = billList;
    }

    @XmlTransient
    public List<Milktea> getMilkteaList() {
        return milkteaList;
    }

    public void setMilkteaList(List<Milktea> milkteaList) {
        this.milkteaList = milkteaList;
    }

    @XmlTransient
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        return "Order1{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", billList=" + billList +
                ", milkteaList=" + milkteaList +
                ", statusorderList=" + statusorderList +
                ", addressShipId=" + addressShipId +
                ", memberId=" + user +
                ", paymentId=" + paymentId +
                '}';
    }
}
