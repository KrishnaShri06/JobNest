package com.jobnest.servlets;

import com.jobnest.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();
        
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("    <title>JobNest - Your Gateway to Career Success</title>");
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
            out.println("            <a href='" + contextPath + "/signup' class='btn-primary'>Sign Up</a>");
        }
        out.println("        </div>");
        out.println("    </nav>");

        // Hero Section
        out.println("    <header class='hero'>");
        out.println("        <div class='container'>");
        out.println("            <h1>Land Your Dream Job Today</h1>");
        out.println("            <p>Connect with the world's most innovative companies and start your next chapter.</p>");
        out.println("            <div class='button-group'>");
        out.println("                <a href='" + contextPath + "/jobs' class='btn-primary'>Browse Available Jobs</a>");
        if (user == null) {
            out.println("                <a href='" + contextPath + "/signup' class='btn-secondary'>Join as Talent</a>");
        }
        out.println("            </div>");
        out.println("        </div>");
        out.println("    </header>");

        // Testimonials
        out.println("    <section class='testimonials'>");
        out.println("        <h2>What Our Users Say</h2>");
        out.println("        <div class='testimonial-grid'>");
        
        out.println("            <div class='testimonial-card'>");
        out.println("                <p>\"JobNest helped me find a role at a top tech company in just two weeks. The process was seamless and incredibly fast!\"</p>");
        out.println("                <div class='author'>Karan Mehra</div>");
        out.println("                <div class='role'>Senior Java Developer</div>");
        out.println("            </div>");

        out.println("            <div class='testimonial-card'>");
        out.println("                <p>\"The best platform for graduates looking for their first break. The job listings are authentic and high-quality.\"</p>");
        out.println("                <div class='author'>Priya Sharma</div>");
        out.println("                <div class='role'>Junior Frontend Engineer</div>");
        out.println("            </div>");

        out.println("            <div class='testimonial-card'>");
        out.println("                <p>\"I love the clean interface and how easily I can track my applications. Highly recommended for every job seeker!\"</p>");
        out.println("                <div class='author'>Rahul Varma</div>");
        out.println("                <div class='role'>Product Manager</div>");
        out.println("            </div>");
        
        out.println("        </div>");
        out.println("    </section>");

        // Footer
        out.println("    <footer class='main-footer'>");
        out.println("        <div class='footer-content'>");
        out.println("            <div class='footer-section'>");
        out.println("                <h3>JobNest</h3>");
        out.println("                <p>Empowering careers and connecting talent with opportunity worldwide.</p>");
        out.println("            </div>");
        out.println("            <div class='footer-section'>");
        out.println("                <h3>Quick Links</h3>");
        out.println("                <ul>");
        out.println("                    <li><a href='#'>About Us</a></li>");
        out.println("                    <li><a href='" + contextPath + "/jobs'>Browse Jobs</a></li>");
        out.println("                    <li><a href='#'>Contact Support</a></li>");
        out.println("                    <li><a href='#'>Privacy Policy</a></li>");
        out.println("                </ul>");
        out.println("            </div>");
        out.println("            <div class='footer-section'>");
        out.println("                <h3>Follow Us</h3>");
        out.println("                <ul>");
        out.println("                    <li><a href='#'>LinkedIn</a></li>");
        out.println("                    <li><a href='#'>Twitter</a></li>");
        out.println("                    <li><a href='#'>Instagram</a></li>");
        out.println("                </ul>");
        out.println("            </div>");
        out.println("        </div>");
        out.println("        <div class='footer-bottom'>");
        out.println("            <p>&copy; 2026 JobNest. Built with ❤️ for Builders.</p>");
        out.println("        </div>");
        out.println("    </footer>");

        out.println("</body></html>");
    }
}
