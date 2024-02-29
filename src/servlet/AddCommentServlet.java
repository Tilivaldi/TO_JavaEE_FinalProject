package servlet;

import db.DBManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Blog;
import model.User;
import model.Comment;

import java.io.IOException;
import java.util.ArrayList;
@WebServlet(value = "/addcomment")
public class AddCommentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String redirect = "/login";
        request.setCharacterEncoding("utf8");
        User currentUser = (User) request.getSession().getAttribute("CURRENT_USER");
        if(currentUser!=null) {
            String commentText = request.getParameter("comment");
            Long blogId = Long.parseLong(request.getParameter("blog_id"));
            Blog blog = DBManager.getBlog(blogId);
            if(blog!=null){
                Comment comment = new Comment();
                comment.setComment(commentText);
                comment.setUser(currentUser);
                comment.setBlog(blog);
                if(DBManager.addComment(comment)){
                    redirect = "/readblog?id="+blogId;
                }
            }
        }
        response.sendRedirect(redirect);
    }
}
