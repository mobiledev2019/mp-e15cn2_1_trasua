package com.example.dell.webservice.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String fullname;
    private String sex;
    private String dob;
    private Integer point;
    private List<Addressship> addressshipList;
    private List<Feedback> feedbackList;
    private Listfavorite listFavoriteId;
    private Account accountId;

    public User(String id, String fullname, String sex, String dob, Integer point, Account accountId) {
        this.id = id;
        this.fullname = fullname;
        this.sex = sex;
        this.dob = dob;
        this.point = point;
        this.accountId = accountId;
    }

    public User(String fullname, String sex, String dob, Account accountId) {
        this.fullname = fullname;
        this.sex = sex;
        this.dob = dob;
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


    public List<Addressship> getAddressshipList() {
        return addressshipList;
    }

    public void setAddressshipList(List<Addressship> addressshipList) {
        this.addressshipList = addressshipList;
    }

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

                ", accountId=" + accountId +
                '}';
    }
}
