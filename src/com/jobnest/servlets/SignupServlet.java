package com.jobnest.servlets;

import com.jobnest.dao.UserDAO;
import com.jobnest.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class SignupServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("    <title>Sign Up - JobNest</title>");
        out.println("    <link rel='stylesheet' href='" + contextPath + "/css/style.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class='navbar'>");
        out.println("        <div class='logo'>🏢 JobNest</div>");
        out.println("        <div class='nav-links'>");
        out.println("            <a href='" + contextPath + "/'>Home</a>");
        out.println("            <a href='" + contextPath + "/jobs'>Browse Jobs</a>");
        out.println("            <a href='" + contextPath + "/login' class='btn-secondary'>Login</a>");
        out.println("        </div>");
        out.println("    </div>");
        
        out.println("    <div class='container'>");
        out.println("        <div class='auth-card'>");
        out.println("            <h1>Join JobNest</h1>");
        out.println("            <p>Create an account to start applying to your dream jobs.</p>");
        
        String error = request.getParameter("error");
        if (error != null) {
            out.println("            <div class='alert error'>❌ " + error + "</div>");
        }

        out.println("            <form action='" + contextPath + "/signup' method='POST'>");
        out.println("                <div class='form-group'>");
        out.println("                    <label for='fullName'>Full Name</label>");
        out.println("                    <input type='text' id='fullName' name='fullName' required placeholder='e.g. John Doe'>");
        out.println("                </div>");
        out.println("                <div class='form-group'>");
        out.println("                    <label for='username'>Username</label>");
        out.println("                    <input type='text' id='username' name='username' required placeholder='Choose a username'>");
        out.println("                </div>");
        out.println("                <div class='form-group'>");
        out.println("                    <label for='email'>Email Address</label>");
        out.println("                    <input type='email' id='email' name='email' required placeholder='john@example.com'>");
        out.println("                </div>");
        out.println("                <div class='form-group'>");
        out.println("                    <label for='password'>Password</label>");
        out.println("                    <input type='password' id='password' name='password' required placeholder='Create a strong password'>");
        out.println("                </div>");
        out.println("                <button type='submit' class='btn-primary'>Create Account</button>");
        out.println("            </form>");
        out.println("            <p class='auth-footer'>Already have an account? <a href='" + contextPath + "/login'>Login here</a></p>");
        out.println("        </div>");
        out.println("    </div>");
        
        out.println("    <footer class='footer'>");
        out.println("        <p>&copy; 2026 JobNest. All rights reserved.</p>");
        out.println("    </footer>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            if (userDAO.isUsernameExists(username)) {
                response.sendRedirect("signup?error=Username already taken");
                return;
            }

            User user = new User();
            user.setFullName(fullName);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password); // Add hashing here for production

            if (userDAO.registerUser(user)) {
                response.sendRedirect("login?success=Account created successfully! Please login.");
            } else {
                response.sendRedirect("signup?error=Registration failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("signup?error=Database error: " + e.getMessage());
        }
    }
}
