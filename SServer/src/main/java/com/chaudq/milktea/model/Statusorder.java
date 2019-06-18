package com.chaudq.milktea.model;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author GomComputer
 */
@Entity
@Table(name = "statusorder")
@XmlRootElement

public class Statusorder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private String id;
    @Column(name = "Status")
    private String status;
    @JoinColumn(name = "OrderId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Order1 orderId;

    public Statusorder() {
    }

    public Statusorder(String id) {
        this.id = id;
    }

    public Statusorder(String id, String status, Order1 orderId) {
        this.id = id;
        this.status = status;
        this.orderId = orderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order1 getOrderId() {
        return orderId;
    }

    public void setOrderId(Order1 orderId) {
        this.orderId = orderId;
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
        if (!(object instanceof Statusorder)) {
            return false;
        }
        Statusorder other = (Statusorder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Statusorder{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", orderId=" + orderId +
                '}';
    }
}
