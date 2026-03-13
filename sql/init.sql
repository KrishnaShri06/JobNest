-- JobNest Database Initialization Script
-- Creates database schema and inserts sample data

-- Create database
CREATE DATABASE IF NOT EXISTS jobnest;
USE jobnest;

-- Create jobs table
CREATE TABLE IF NOT EXISTS jobs (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    company VARCHAR(150) NOT NULL,
    description TEXT NOT NULL,
    location VARCHAR(100) NOT NULL,
    salary VARCHAR(50),
    posted_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Modify applications table to link to users
DROP TABLE IF EXISTS applications;
CREATE TABLE IF NOT EXISTS applications (
    id INT PRIMARY KEY AUTO_INCREMENT,
    job_id INT NOT NULL,
    user_id INT NOT NULL,
    applicant_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    resume_text TEXT,
    status VARCHAR(20) DEFAULT 'APPLIED',
    applied_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert sample job data
INSERT INTO jobs (title, company, description, location, salary) VALUES
('Java Developer', 'Tech Solutions Inc', 'Looking for a skilled Java developer with experience in web applications. Must know Servlets, JDBC, and MySQL.', 'Bangalore', '₹6-8 LPA'),
('Frontend Developer', 'WebCraft Ltd', 'Need a creative frontend developer proficient in HTML, CSS, and JavaScript. Experience with responsive design required.', 'Mumbai', '₹5-7 LPA'),
('Full Stack Developer', 'InnovateTech', 'Seeking a full stack developer with Java backend and modern frontend skills. Experience with databases is a must.', 'Hyderabad', '₹8-12 LPA'),
('Software Engineer Intern', 'StartUp Hub', 'Great opportunity for freshers! Learn core web development with Java Servlets and database integration.', 'Pune', '₹3-4 LPA'),
('Database Administrator', 'DataCore Systems', 'Looking for a DBA with MySQL expertise. Knowledge of query optimization and database design required.', 'Chennai', '₹7-10 LPA'),
('Backend Developer', 'Cloud Nine Technologies', 'Backend developer needed for building scalable web applications. Java, Spring, and database skills required.', 'Bangalore', '₹9-14 LPA');

-- Verify data
SELECT * FROM jobs;
