package com.hencesimplified.praveenassignment.model;

public class Doctor {
    String emailId;
    String doctorId;
    String doctorName;

    public Doctor() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Doctor(String emailId, String doctorId, String doctorName) {
        this.emailId = emailId;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
    }
}
