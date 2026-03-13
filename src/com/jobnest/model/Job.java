package com.jobnest.model;

import java.sql.Timestamp;

/**
 * Job Model Class
 * Represents a job listing entity
 */
public class Job {
    
    private int id;
    private String title;
    private String company;
    private String description;
    private String location;
    private String salary;
    private Timestamp postedDate;
    
    // Default constructor
    public Job() {
    }
    
    // Parameterized constructor
    public Job(int id, String title, String company, String description, 
               String location, String salary, Timestamp postedDate) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.description = description;
        this.location = location;
        this.salary = salary;
        this.postedDate = postedDate;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getCompany() {
        return company;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getSalary() {
        return salary;
    }
    
    public void setSalary(String salary) {
        this.salary = salary;
    }
    
    public Timestamp getPostedDate() {
        return postedDate;
    }
    
    public void setPostedDate(Timestamp postedDate) {
        this.postedDate = postedDate;
    }
    
    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }
}
