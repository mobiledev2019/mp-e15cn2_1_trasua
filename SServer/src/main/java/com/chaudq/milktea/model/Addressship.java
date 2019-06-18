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
@Table(name = "addressship")
@XmlRootElement


public class Addressship implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private String id;
    @Column(name = "City")
    private String city;
    @Column(name = "Town")
    private String town;
    @Column(name = "Description")
    private String description;
    @JoinColumn(name = "UserId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private User userId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "addressShipId")
    private List<Order1> order1List;

    public Addressship(String id, String city, String town, String description) {
        this.id = id;
        this.city = city;
        this.town = town;
        this.description = description;
    }

    public Addressship() {
    }

    public Addressship(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @XmlTransient
    public List<Order1> getOrder1List() {
        return order1List;
    }

    public void setOrder1List(List<Order1> order1List) {
        this.order1List = order1List;
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
        if (!(object instanceof Addressship)) {
            return false;
        }
        Addressship other = (Addressship) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Addressship{" +
                "id='" + id + '\'' +
                ", city='" + city + '\'' +
                ", town='" + town + '\'' +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", order1List=" + order1List +
                '}';
    }
}
