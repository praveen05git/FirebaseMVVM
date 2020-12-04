package com.hencesimplified.praveenassignment.model;

public class Patient {
    String emailId;
    String patientId;
    String patientName;
    String doctorId;
    String cause;

    public Patient() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Patient(String emailId, String patientId, String patientName, String doctorId, String cause) {
        this.emailId = emailId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.cause = cause;
    }
}
