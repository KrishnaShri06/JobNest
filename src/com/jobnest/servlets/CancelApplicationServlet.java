package com.jobnest.servlets;

import com.jobnest.dao.ApplicationDAO;
import com.jobnest.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet to handle application cancellation
 */
public class CancelApplicationServlet extends HttpServlet {
    
    private ApplicationDAO applicationDAO;
    
    @Override
    public void init() throws ServletException {
        applicationDAO = new ApplicationDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        String appIdParam = request.getParameter("appId");
        if (appIdParam != null && !appIdParam.isEmpty()) {
            try {
                int appId = Integer.parseInt(appIdParam);
                boolean success = applicationDAO.updateApplicationStatus(appId, user.getId(), "CANCELLED");
                
                if (success) {
                    response.sendRedirect("profile?message=Application cancelled successfully");
                } else {
                    response.sendRedirect("profile?error=Failed to cancel application");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("profile?error=An error occurred");
            }
        } else {
            response.sendRedirect("profile");
        }
    }
}
