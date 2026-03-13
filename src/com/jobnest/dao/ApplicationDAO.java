package com.jobnest.dao;

import com.jobnest.model.Application;
import com.jobnest.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Application operations
 * Handles all database operations related to job applications
 */
public class ApplicationDAO {
    
    /**
     * Save a new job application to database
     * @param application Application object to save
     * @return true if successful, false otherwise
     */
    public boolean saveApplication(Application application) throws SQLException {
        String sql = "INSERT INTO applications (job_id, user_id, applicant_name, email, phone, resume_text, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, application.getJobId());
            pstmt.setInt(2, application.getUserId());
            pstmt.setString(3, application.getApplicantName());
            pstmt.setString(4, application.getEmail());
            pstmt.setString(5, application.getPhone());
            pstmt.setString(6, application.getResumeText());
            pstmt.setString(7, application.getStatus());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Get all applications submitted by a specific user
     */
    public List<Application> getApplicationsByUserId(int userId) throws SQLException {
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT * FROM applications WHERE user_id = ? ORDER BY applied_date DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    applications.add(new Application(
                        rs.getInt("id"),
                        rs.getInt("job_id"),
                        rs.getInt("user_id"),
                        rs.getString("applicant_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("resume_text"),
                        rs.getString("status"),
                        rs.getTimestamp("applied_date")
                    ));
                }
            }
        }
        return applications;
    }

    /**
     * Update application status (e.g., Cancel)
     */
    public boolean updateApplicationStatus(int applicationId, int userId, String status) throws SQLException {
        String sql = "UPDATE applications SET status = ? WHERE id = ? AND user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, applicationId);
            pstmt.setInt(3, userId);
            
            return pstmt.executeUpdate() > 0;
        }
    }
}
