package com.chaudq.milktea.model;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author GomComputer
 */
@Entity
@Table(name = "milktea")
@XmlRootElement

public class Milktea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private String id;
    @Basic(optional = false)
    @Column(name = "Size")
    private String size;
    @JoinColumn(name = "ToppingId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Topping topping;
    @JoinColumn(name = "OrderId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Order1 orderId;
    @JoinColumn(name = "TeaId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Tea teaId;

    public Milktea() {
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

    public void setTopping(Topping toppingId) {
        this.topping = toppingId;
    }

    public Order1 getOrder1() {
        return orderId;
    }

    public void setOrder1(Order1 orderId) {
        this.orderId = orderId;
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
                ", toppingId=" + topping +
                ", orderId=" + orderId +
                ", teaId=" + teaId +
                '}';
    }
}
