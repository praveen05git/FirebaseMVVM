package com.hencesimplified.praveenassignment.model;

public class User {
    String emailId;
    String password;

    public User() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }
}
