package com.jobnest.dao;

import com.jobnest.model.Job;
import com.jobnest.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Job operations
 * Handles all database operations related to jobs
 */
public class JobDAO {
    
    /**
     * Retrieve all jobs from database
     * @return List of all jobs
     */
    public List<Job> getAllJobs() throws SQLException {
        List<Job> jobs = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM jobs ORDER BY posted_date DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Job job = new Job();
                job.setId(rs.getInt("id"));
                job.setTitle(rs.getString("title"));
                job.setCompany(rs.getString("company"));
                job.setDescription(rs.getString("description"));
                job.setLocation(rs.getString("location"));
                job.setSalary(rs.getString("salary"));
                job.setPostedDate(rs.getTimestamp("posted_date"));
                
                jobs.add(job);
            }
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return jobs;
    }
    
    /**
     * Get a specific job by ID
     * @param jobId Job ID
     * @return Job object or null if not found
     */
    public Job getJobById(int jobId) throws SQLException {
        Job job = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM jobs WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, jobId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                job = new Job();
                job.setId(rs.getInt("id"));
                job.setTitle(rs.getString("title"));
                job.setCompany(rs.getString("company"));
                job.setDescription(rs.getString("description"));
                job.setLocation(rs.getString("location"));
                job.setSalary(rs.getString("salary"));
                job.setPostedDate(rs.getTimestamp("posted_date"));
            }
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return job;
    }
}
