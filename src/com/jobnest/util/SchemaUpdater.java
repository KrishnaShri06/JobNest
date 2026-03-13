package com.jobnest.util;

import java.sql.Connection;
import java.sql.Statement;

public class SchemaUpdater {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "ALTER TABLE applications ADD COLUMN status VARCHAR(20) DEFAULT 'APPLIED' AFTER resume_text";
            stmt.executeUpdate(sql);
            System.out.println("Schema updated successfully: added 'status' column.");
            conn.close();
        } catch (Exception e) {
            System.err.println("Migration failed (possibly column already exists): " + e.getMessage());
        }
    }
}
