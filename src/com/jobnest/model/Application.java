package com.jobnest.model;

import java.sql.Timestamp;

/**
 * Application Model Class
 * Represents a job application entity
 */
public class Application {
    
    private int id;
    private int jobId;
    private int userId;
    private String applicantName;
    private String email;
    private String phone;
    private String resumeText;
    private String status;
    private Timestamp appliedDate;
    
    // Default constructor
    public Application() {
    }
    
    // Parameterized constructor
    public Application(int id, int jobId, int userId, String applicantName, String email, 
                      String phone, String resumeText, String status, Timestamp appliedDate) {
        this.id = id;
        this.jobId = jobId;
        this.userId = userId;
        this.applicantName = applicantName;
        this.email = email;
        this.phone = phone;
        this.resumeText = resumeText;
        this.status = status;
        this.appliedDate = appliedDate;
    }
    
    // Constructor without id (for new applications)
    public Application(int jobId, int userId, String applicantName, String email, 
                      String phone, String resumeText, String status) {
        this.jobId = jobId;
        this.userId = userId;
        this.applicantName = applicantName;
        this.email = email;
        this.phone = phone;
        this.resumeText = resumeText;
        this.status = status;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getJobId() {
        return jobId;
    }
    
    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getApplicantName() {
        return applicantName;
    }
    
    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getResumeText() {
        return resumeText;
    }
    
    public void setResumeText(String resumeText) {
        this.resumeText = resumeText;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(Timestamp appliedDate) {
        this.appliedDate = appliedDate;
    }
    
    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", jobId=" + jobId +
                ", status='" + status + '\'' +
                ", applicantName='" + applicantName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
