package servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import db.DBManager;
import model.Blog;
import model.User;

@WebServlet(value = "/addblog")
public class AddBlogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("CURRENT_USER");
        if(currentUser!=null) {
            request.getRequestDispatcher("/addblog.jsp").forward(request, response);
        }else{
            response.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String redirect = "/login";
        request.setCharacterEncoding("utf8");
        User currentUser = (User) request.getSession().getAttribute("CURRENT_USER");
        if(currentUser!=null) {
            redirect = "/addblog?error";
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            Blog blog = new Blog();
            blog.setTitle(title);
            blog.setContent(content);
            blog.setUser(currentUser);
            if(DBManager.addBlog(blog)){
                redirect = "/addblog?success";
            }
        }
        response.sendRedirect(redirect);
    }
}