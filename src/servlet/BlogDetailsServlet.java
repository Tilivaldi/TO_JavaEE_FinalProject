package servlet;
import db.DBManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Blog;
import model.Comment;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(value = "/readblog")
public class BlogDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Blog blog = DBManager.getBlog(id);
        request.setAttribute("blog", blog);
        if(blog!=null){
            ArrayList<Comment> comments = DBManager.getAllComments(blog.getId());
            request.setAttribute("comments", comments);
        }
        request.getRequestDispatcher("/blogdetails.jsp").forward(request, response);
    }
}