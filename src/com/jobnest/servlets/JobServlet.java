package com.jobnest.servlets;

import com.jobnest.dao.JobDAO;
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

public class JobServlet extends HttpServlet {
    private JobDAO jobDAO = new JobDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();
        
        List<Job> jobList = null;
        String errorMessage = null;
        
        try {
            jobList = jobDAO.getAllJobs();
        } catch (SQLException e) {
            errorMessage = e.getMessage();
        }

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("    <title>Browse Jobs - JobNest</title>");
        out.println("    <link rel='stylesheet' href='" + contextPath + "/css/style.css'>");
        out.println("</head>");
        out.println("<body>");

        // Navbar
        out.println("    <nav class='navbar'>");
        out.println("        <div class='logo'>🏢 JobNest</div>");
        out.println("        <div class='nav-links'>");
        out.println("            <a href='" + contextPath + "/home'>Home</a>");
        out.println("            <a href='" + contextPath + "/jobs'>Browse Jobs</a>");
        if (user != null) {
            out.println("            <span class='user-info'>👤 " + user.getFullName() + "</span>");
            out.println("            <a href='" + contextPath + "/profile'>Dashboard</a>");
            out.println("            <a href='" + contextPath + "/logout' class='btn-secondary'>Logout</a>");
        } else {
            out.println("            <a href='" + contextPath + "/login'>Login</a>");
            out.println("            <a href='" + contextPath + "/signup' class='btn-primary' style='padding: 8px 15px; margin-top:0;'>Sign Up</a>");
        }
        out.println("        </div>");
        out.println("    </nav>");

        out.println("    <div class='container' style='margin-top: 40px;'>");
        
        if (errorMessage != null) {
            out.println("        <div class='jobs-section'>");
            out.println("            <h1 class='section-title'>Error Loading Jobs</h1>");
            out.println("            <div class='alert error'>");
            out.println("                <strong>Message:</strong> " + errorMessage);
            out.println("                <p style='margin-top: 10px;'>Please check your database settings.</p>");
            out.println("            </div>");
            out.println("        </div>");
        } else {
            out.println("        <main>");
            out.println("            <h1 class='section-title' style='color: white; border-bottom: 2px solid rgba(255,255,255,0.3);'>Available Positions</h1>");
            
            if (jobList == null || jobList.isEmpty()) {
                out.println("            <div class='jobs-section'>");
                out.println("                <div class='no-jobs'>");
                out.println("                    <p>No jobs available at the moment. Please check back later!</p>");
                out.println("                </div>");
                out.println("            </div>");
            } else {
                out.println("            <div class='jobs-grid'>");
                
                for (Job job : jobList) {
                    out.println("                <div class='job-card'>");
                    out.println("                    <div class='job-header'>");
                    out.println("                        <h2 class='job-title'>" + job.getTitle() + "</h2>");
                    out.println("                        <div class='company-name'>" + job.getCompany() + "</div>");
                    out.println("                    </div>");
                    out.println("                    <p class='job-description'>" + job.getDescription() + "</p>");
                    out.println("                    <div class='job-details'>");
                    out.println("                        <div class='detail'>📍 <strong>Location:</strong> " + job.getLocation() + "</div>");
                    out.println("                        <div class='detail'>💰 <strong>Salary:</strong> " + job.getSalary() + "</div>");
                    out.println("                        <div class='detail'>📅 <strong>Posted:</strong> " + job.getPostedDate() + "</div>");
                    out.println("                    </div>");
                    out.println("                    <a href='" + contextPath + "/apply?jobId=" + job.getId() + "' class='apply-btn'>Apply Now</a>");
                    out.println("                </div>");
                }
                
                out.println("            </div>");
            }
            out.println("        </main>");
        }
        
        out.println("    </div>");
        
        out.println("    <footer class='main-footer'>");
        out.println("        <p>&copy; 2026 JobNest. All rights reserved.</p>");
        out.println("    </footer>");
        out.println("</body></html>");
    }
}
