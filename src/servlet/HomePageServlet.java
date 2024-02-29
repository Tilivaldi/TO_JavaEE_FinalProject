package servlet;

import db.DBManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Blog;
import model.User;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;


@WebServlet(value = "/homepage")
public class HomePageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {



//        HttpSession session = request.getSession();
        User currentUser = (User) request.getSession().getAttribute("CURRENT_USER");
        if (currentUser == null) {
            response.sendRedirect("/login");
        } else {
            ArrayList<Blog> blogs = DBManager.getAllBlogs();
            request.setAttribute("blogi", blogs);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }


}