package com.jobnest.servlets;

import com.jobnest.dao.ApplicationDAO;
import com.jobnest.dao.JobDAO;
import com.jobnest.model.Application;
import com.jobnest.model.Job;
import com.jobnest.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class ProfileServlet extends HttpServlet {
    private ApplicationDAO applicationDAO = new ApplicationDAO();
    private JobDAO jobDAO = new JobDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect("login?error=Please login to view your profile");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("    <title>My Dashboard - JobNest</title>");
        out.println("    <link rel='stylesheet' href='" + contextPath + "/css/style.css'>");
        out.println("</head>");
        out.println("<body>");

        // Navbar
        out.println("    <nav class='navbar'>");
        out.println("        <div class='logo'>🏢 JobNest</div>");
        out.println("        <div class='nav-links'>");
        out.println("            <a href='" + contextPath + "/home'>Home</a>");
        out.println("            <a href='" + contextPath + "/jobs'>Browse Jobs</a>");
        out.println("            <span class='user-info'>👤 " + user.getFullName() + "</span>");
        out.println("            <a href='" + contextPath + "/logout' class='btn-secondary'>Logout</a>");
        out.println("        </div>");
        out.println("    </nav>");

        out.println("    <div class='container'>");
        out.println("        <header style='margin-bottom: 40px; color: white;'>");
        out.println("            <h1>User Dashboard</h1>");
        out.println("            <p>Welcome back, " + user.getFullName() + "! Manage your applications below.</p>");
        out.println("        </header>");

        // Stats Section
        out.println("        <div class='dashboard-grid'>");
        try {
            List<Application> apps = applicationDAO.getApplicationsByUserId(user.getId());
            
            out.println("            <div class='stat-card'>");
            out.println("                <h3>Total Applications</h3>");
            out.println("                <div class='number'>" + apps.size() + "</div>");
            out.println("            </div>");
            
            out.println("            <div class='stat-card'>");
            out.println("                <h3>Account Status</h3>");
            out.println("                <div class='number' style='font-size: 1.5rem; color: #4caf50;'>Active</div>");
            out.println("            </div>");

            out.println("        </div>");

            // Applications Table
            out.println("        <div class='jobs-section' style='margin-top: 40px;'>");
            out.println("            <h2 class='section-title'>My Recent Applications</h2>");
            
            if (apps.isEmpty()) {
                out.println("            <div class='no-jobs'>");
                out.println("                <p>You haven't applied to any jobs yet.</p>");
                out.println("                <a href='" + contextPath + "/jobs' class='btn-primary'>Find Jobs Now</a>");
                out.println("            </div>");
            } else {
                // Alert messages
                String message = request.getParameter("message");
                String error = request.getParameter("error");
                if (message != null) out.println("<div class='alert success'>✅ " + message + "</div>");
                if (error != null) out.println("<div class='alert error'>❌ " + error + "</div>");

                out.println("            <table class='dashboard-table'>");
                out.println("                <thead>");
                out.println("                    <tr>");
                out.println("                        <th>Job Title</th>");
                out.println("                        <th>Company</th>");
                out.println("                        <th>Status</th>");
                out.println("                        <th>Applied Date</th>");
                out.println("                        <th>Action</th>");
                out.println("                    </tr>");
                out.println("                </thead>");
                out.println("                <tbody>");
                
                for (Application app : apps) {
                    Job job = jobDAO.getJobById(app.getJobId());
                    String jobTitle = (job != null) ? job.getTitle() : "N/A";
                    String company = (job != null) ? job.getCompany() : "N/A";
                    String statusClass = "status-" + app.getStatus().toLowerCase();
                    
                    out.println("                    <tr>");
                    out.println("                        <td class='job-title-cell'>" + jobTitle + "</td>");
                    out.println("                        <td class='company-cell'>" + company + "</td>");
                    out.println("                        <td><span class='status-badge " + statusClass + "'>" + app.getStatus() + "</span></td>");
                    out.println("                        <td class='date-cell'>" + app.getAppliedDate() + "</td>");
                    out.println("                        <td>");
                    if ("APPLIED".equals(app.getStatus())) {
                        out.println("                            <form action='" + contextPath + "/cancel-application' method='POST' style='display:inline;'>");
                        out.println("                                <input type='hidden' name='appId' value='" + app.getId() + "'>");
                        out.println("                                <button type='submit' class='btn-cancel' onclick='return confirm(\"Are you sure you want to withdraw this application?\")'>Withdraw</button>");
                        out.println("                            </form>");
                    } else {
                        out.println("                            <span class='text-muted'>No Actions</span>");
                    }
                    out.println("                        </td>");
                    out.println("                    </tr>");
                }
                
                out.println("                </tbody>");
                out.println("            </table>");
            }
            out.println("        </div>");

        } catch (SQLException e) {
            out.println("            <div class='alert error'>❌ Error loading dashboard data: " + e.getMessage() + "</div>");
        }

        out.println("    </div>");

        out.println("    <footer class='main-footer' style='margin-top: 100px;'>");
        out.println("        <p>&copy; 2026 JobNest. All rights reserved.</p>");
        out.println("    </footer>");
        out.println("</body></html>");
    }
}
