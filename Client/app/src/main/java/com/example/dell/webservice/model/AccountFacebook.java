package com.example.dell.webservice.model;

import java.io.Serializable;

public class AccountFacebook implements Serializable {
    private String name;
    private String email;
    private String gender;
    private String dob;

    public AccountFacebook(String name, String email, String gender, String dob) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
    }

    public AccountFacebook() {   }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
