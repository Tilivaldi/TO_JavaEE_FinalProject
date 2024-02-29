package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import db.DBManager;
import model.User;

@WebServlet(value = "/register")
public class AddUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String redirect = "/register?emailerror";
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rePassword = request.getParameter("re_password");
        String fullName = request.getParameter("full_name");
        User checkUser = DBManager.getUser(email);
        if (checkUser == null) {
            redirect = "/register?passworderror";
            if (Objects.equals(password, rePassword)) {
                User user = new User();
                user.setEmail(email);
                user.setPassword(password);
                user.setFullName(fullName);
                DBManager.addUser(user);
                redirect = "/register?success";
            }

        }
        response.sendRedirect(redirect);
    }

}