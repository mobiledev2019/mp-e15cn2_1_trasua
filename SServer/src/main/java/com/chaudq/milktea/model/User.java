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
@Table(name = "user")
@XmlRootElement

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private String id;
    @Column(name = "Fullname")
    private String fullname;
    @Column(name = "Sex")
    private String sex;
    @Column(name = "Dob")
    private String dob;
    @Column(name = "Point")
    private Integer point;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<Addressship> addressshipList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<Feedback> feedbackList;
    @JoinColumn(name = "ListFavoriteId", referencedColumnName = "Id")
    @ManyToOne
    private Listfavorite listFavoriteId;
    @JoinColumn(name = "AccountId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Account accountId;

    public User(String id, String fullname, String sex, String dob, Integer point, Account accountId) {
        this.id = id;
        this.fullname = fullname;
        this.sex = sex;
        this.dob = dob;
        this.point = point;
        this.accountId = accountId;
    }

    public User(String id, String fullname, String sex, String dob, Integer point, Listfavorite listFavoriteId, Account accountId) {
        this.id = id;
        this.fullname = fullname;
        this.sex = sex;
        this.dob = dob;
        this.point = point;
        this.listFavoriteId = listFavoriteId;
        this.accountId = accountId;
    }

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    @XmlTransient
    public List<Addressship> getAddressshipList() {
        return addressshipList;
    }

    public void setAddressshipList(List<Addressship> addressshipList) {
        this.addressshipList = addressshipList;
    }

    @XmlTransient
    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public Listfavorite getListFavoriteId() {
        return listFavoriteId;
    }

    public void setListFavoriteId(Listfavorite listFavoriteId) {
        this.listFavoriteId = listFavoriteId;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", fullname='" + fullname + '\'' +
                ", sex='" + sex + '\'' +
                ", dob='" + dob + '\'' +
                ", point=" + point +
                ", addressshipList=" + addressshipList +
                ", feedbackList=" + feedbackList +
                ", listFavoriteId=" + listFavoriteId +
                ", accountId=" + accountId +
                '}';
    }
}
