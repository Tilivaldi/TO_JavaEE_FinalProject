package db;

import model.Comment;
import model.User;
import model.Blog;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class DBManager {
    private static Connection connection;
    static {
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/FinalProject",
                    "postgres",
                    "123456"
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static User getUser(String email){
        User user = null;
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE email = ? LIMIT 1");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                user = new User();
                user.setEmail(resultSet.getString("email"));
                user.setId(resultSet.getLong("id"));
                user.setPassword(resultSet.getString("password"));
                user.setFullName(resultSet.getString("full_name"));
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public static boolean addUser(User user){
        int rows = 0;
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (email, password, full_name) " +
                    "VALUES (?, ?, ?)");
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFullName());
            rows = statement.executeUpdate();
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rows>0;
    }

    public static boolean addBlog(Blog blog){
        int rows = 0;
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO blogs (user_id, title, content, post_date) " +
                    "VALUES (?, ?, ?, NOW())");
            statement.setLong(1, blog.getUser().getId());
            statement.setString(2, blog.getTitle());
            statement.setString(3, blog.getContent());
            rows = statement.executeUpdate();
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rows>0;
    }

    public static ArrayList<Blog> getAllBlogs(){
        ArrayList<Blog> blogs = new ArrayList<>();
        try{
//            PreparedStatement statement = connection.prepareStatement(
//                    "SELECT b.id, b.title, b.content, b.post_date, u.id, u.full_name, u.email " +
//                    "FROM blogs b " +
//                    "INNER JOIN users u ON u.id = b.user_id " +
//                    "ORDER BY b.post_date DESC ");
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM blogs");
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Blog blog = new Blog();
                blog.setId(resultSet.getLong("id"));
                blog.setTitle(resultSet.getString("title"));
                blog.setContent(resultSet.getString("content"));
                blog.setPostDate(resultSet.getTimestamp("post_date"));
//                User user = new User();
//                user.setId(resultSet.getLong("id"));
//                user.setEmail(resultSet.getString("email"));
//                user.setFullName(resultSet.getString("full_name"));
//                blog.setUser(user);
                blogs.add(blog);
            }
//            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return blogs;
    }

    public static Blog getBlog(Long id){
        Blog blog = null;
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT b.id, b.title, b.content, b.post_date, b.user_id, u.full_name, u.email " +
                    "FROM blogs b " +
                    "INNER JOIN users u ON u.id = b.user_id " +
                    "WHERE b.id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                blog = new Blog();
                blog.setId(resultSet.getLong("id"));
                blog.setTitle(resultSet.getString("title"));
                blog.setContent(resultSet.getString("content"));
                blog.setPostDate(resultSet.getTimestamp("post_date"));
                User user = new User();
                user.setId(resultSet.getLong("user_id"));
                user.setEmail(resultSet.getString("email"));
                user.setFullName(resultSet.getString("full_name"));
                blog.setUser(user);
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return blog;
    }

    public static boolean addComment(Comment comment){
        int rows = 0;
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO comments (user_id, blog_id, comment, post_date) " +
                    "VALUES (?, ?, ?, NOW())");
            statement.setLong(1, comment.getUser().getId());
            statement.setLong(2, comment.getBlog().getId());
            statement.setString(3, comment.getComment());
            rows = statement.executeUpdate();
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rows>0;
    }



    public static ArrayList<Comment> getAllComments(Long blogId){
        ArrayList<Comment> comments = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT c.id, c.user_id, c.comment, c.blog_id, u.full_name, u.email, c.post_date " +
                    "FROM comments c " +
                    "INNER JOIN users u ON c.user_id = u.id " +
                    "WHERE c.blog_id = ? " +
                    "ORDER BY c.post_date DESC");
            statement.setLong(1, blogId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Comment comment = new Comment();
                comment.setId(resultSet.getLong("id"));
                comment.setComment(resultSet.getString("comment"));
                comment.setPostDate(resultSet.getTimestamp("post_date"));
                User user = new User();
                user.setId(resultSet.getLong("user_id"));
                user.setFullName(resultSet.getString("full_name"));
                user.setEmail(resultSet.getString("email"));
                comment.setUser(user);
                Blog blog = new Blog();
                blog.setId(resultSet.getLong("blog_id"));
                comment.setBlog(blog);
                comments.add(comment);
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return comments;
    }

}
