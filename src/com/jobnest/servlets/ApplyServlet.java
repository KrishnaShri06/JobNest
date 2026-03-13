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

/**
 * Servlet to handle job application form and submission
 */
public class ApplyServlet extends HttpServlet {
    
    private JobDAO jobDAO;
    private ApplicationDAO applicationDAO;
    
    @Override
    public void init() throws ServletException {
        jobDAO = new JobDAO();
        applicationDAO = new ApplicationDAO();
    }
    
    /**
     * Display application form
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        String contextPath = request.getContextPath();

        if (user == null) {
            response.sendRedirect("login?error=You must be logged in to apply for jobs");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String jobIdParam = request.getParameter("jobId");
        
        if (jobIdParam == null || jobIdParam.isEmpty()) {
            response.sendRedirect("jobs");
            return;
        }
        
        try {
            int jobId = Integer.parseInt(jobIdParam);
            Job job = jobDAO.getJobById(jobId);
            
            if (job == null) {
                response.sendRedirect("jobs");
                return;
            }
            
            // Generate application form HTML
            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("    <meta charset='UTF-8'>");
            out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("    <title>Apply for " + job.getTitle() + " - JobNest</title>");
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
            
            // Main content
            out.println("    <main class='container' style='margin-top: 40px;'>");
            out.println("        <div class='form-container'>");
            out.println("            <div class='job-info-box'>");
            out.println("                <h2>Applying for:</h2>");
            out.println("                <h3 class='job-title'>" + job.getTitle() + "</h3>");
            out.println("                <p class='company-name'>" + job.getCompany() + "</p>");
            out.println("                <p class='job-location'>📍 " + job.getLocation() + " | 💰 " + job.getSalary() + "</p>");
            out.println("            </div>");
            
            out.println("            <form action='" + contextPath + "/apply' method='POST'>");
            out.println("                <input type='hidden' name='jobId' value='" + job.getId() + "'>");
            out.println("                ");
            out.println("                <h2 class='form-title'>Application Form</h2>");
            out.println("                ");
            out.println("                <div class='form-group'>");
            out.println("                    <label for='name'>Full Name</label>");
            out.println("                    <input type='text' id='name' name='name' value='" + user.getFullName() + "' required readonly>");
            out.println("                </div>");
            out.println("                ");
            out.println("                <div class='form-group'>");
            out.println("                    <label for='email'>Email Address</label>");
            out.println("                    <input type='email' id='email' name='email' value='" + user.getEmail() + "' required readonly>");
            out.println("                </div>");
            out.println("                ");
            out.println("                <div class='form-group'>");
            out.println("                    <label for='phone'>Phone Number *</label>");
            out.println("                    <input type='tel' id='phone' name='phone' required placeholder='Enter your contact number'>");
            out.println("                </div>");
            out.println("                ");
            out.println("                <div class='form-group'>");
            out.println("                    <label for='resume'>Cover Letter / Resume Summary *</label>");
            out.println("                    <textarea id='resume' name='resume' rows='6' required " +
                       "placeholder='Briefly describe your experience and why you are a fit...'></textarea>");
            out.println("                </div>");
            out.println("                ");
            out.println("                <button type='submit' class='submit-btn'>Submit Application</button>");
            out.println("            </form>");
            out.println("        </div>");
            out.println("    </main>");
            
            // Footer
            out.println("    <footer class='main-footer'>");
            out.println("        <p>&copy; 2026 JobNest. All rights reserved.</p>");
            out.println("    </footer>");
            out.println("</body></html>");
            
        } catch (NumberFormatException e) {
            response.sendRedirect("jobs");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
    
    /**
     * Process form submission
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        
        if (user == null) {
            response.sendRedirect("login?error=Session expired. Please login again.");
            return;
        }

        try {
            int jobId = Integer.parseInt(request.getParameter("jobId"));
            String phone = request.getParameter("phone");
            String resume = request.getParameter("resume");
            
            // Create application object with userId
            Application application = new Application(
                jobId, 
                user.getId(), 
                user.getFullName(), 
                user.getEmail(), 
                phone.trim(), 
                resume != null ? resume.trim() : "",
                "APPLIED"
            );
            
            // Save to database
            boolean success = applicationDAO.saveApplication(application);
            
            if (success) {
                response.sendRedirect("success.html");
            } else {
                response.sendRedirect("error.html");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
