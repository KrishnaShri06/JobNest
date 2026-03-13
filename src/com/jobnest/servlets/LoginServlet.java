package com.jobnest.servlets;

import com.jobnest.dao.UserDAO;
import com.jobnest.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
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
        out.println("    <title>Login - JobNest</title>");
        out.println("    <link rel='stylesheet' href='" + contextPath + "/css/style.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class='navbar'>");
        out.println("        <div class='logo'>🏢 JobNest</div>");
        out.println("        <div class='nav-links'>");
        out.println("            <a href='" + contextPath + "/'>Home</a>");
        out.println("            <a href='" + contextPath + "/jobs'>Browse Jobs</a>");
        out.println("            <a href='" + contextPath + "/signup' class='btn-secondary'>Sign Up</a>");
        out.println("        </div>");
        out.println("    </div>");
        
        out.println("    <div class='container'>");
        out.println("        <div class='auth-card'>");
        out.println("            <h1>Welcome Back</h1>");
        out.println("            <p>Login to manage your applications and profiles.</p>");
        
        String error = request.getParameter("error");
        if (error != null) {
            out.println("            <div class='alert error'>❌ " + error + "</div>");
        }
        
        String success = request.getParameter("success");
        if (success != null) {
            out.println("            <div class='alert success'>✅ " + success + "</div>");
        }

        out.println("            <form action='" + contextPath + "/login' method='POST'>");
        out.println("                <div class='form-group'>");
        out.println("                    <label for='username'>Username</label>");
        out.println("                    <input type='text' id='username' name='username' required placeholder='Enter your username'>");
        out.println("                </div>");
        out.println("                <div class='form-group'>");
        out.println("                    <label for='password'>Password</label>");
        out.println("                    <input type='password' id='password' name='password' required placeholder='Enter your password'>");
        out.println("                </div>");
        out.println("                <button type='submit' class='btn-primary'>Login</button>");
        out.println("            </form>");
        out.println("            <p class='auth-footer'>Don't have an account? <a href='" + contextPath + "/signup'>Sign up here</a></p>");
        out.println("        </div>");
        out.println("    </div>");
        
        out.println("    <footer class='footer'>");
        out.println("        <p>&copy; 2026 JobNest. All rights reserved.</p>");
        out.println("    </footer>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userDAO.loginUser(username, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("jobs");
            } else {
                response.sendRedirect("login?error=Invalid username or password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login?error=Database error: " + e.getMessage());
        }
    }
}
