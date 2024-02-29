<%@ page import="model.Blog" %>
<%@ page import="model.Comment" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Home
  Date: 28.02.2024
  Time: 23:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="head.jsp"%>
</head>
<body>
<%@include file="header.jsp"%>
<div class="container" style="min-height: 500px;">
    <div class="row mt-3">
        <div class="col-12">
            <%
                Blog blog = (Blog) request.getAttribute("blog");
                if(blog!=null){
            %>

            <div class="row mt-3">
                <div class="col-11 mx-auto p-3" style="background-color: lightgrey;">
                    <h2><%=blog.getTitle()%></h2>
                    <p class="mt-2"><%=blog.getContent()%></p>
                    <p class="mt-2">
                        Posted by <strong><%=blog.getUser().getFullName()%></strong>
                        at <strong><%=blog.getPostDate()%></strong>
                    </p>

                    <%
                        if(currentUser!=null){
                    %>
                    <div class="row mt-2">
                        <div class="col-12">
                            <form action="/addcomment" method="post">
                                <input type="hidden" name="blog_id" value="<%=blog.getId()%>">
                                <textarea class="form-control" name="comment" placeholder="Write a comment"></textarea>
                                <button class="btn btn-success mt-3">ADD COMMENT</button>
                            </form>
                        </div>
                    </div>
                    <%
                        }
                    %>
                    <hr>
                    <%
                        ArrayList<Comment> comments = (ArrayList<Comment>) request.getAttribute("comments");
                        if(comments!=null){
                            for(Comment comment : comments){
                    %>

                    <div class="row mt-2">
                        <div class="col-12">
                            <h5><%=comment.getUser().getFullName()%></h5>
                            <p><%=comment.getComment()%></p>
                            <p>At <strong><%=comment.getPostDate()%></strong></p>
                        </div>
                    </div>
                    <%
                            }
                        }
                    %>
                </div>
            </div>
            <%
                }
            %>
        </div>
    </div>
</div>
<%@include file="footer.jsp"%>
</body>

<%@include file="foot.jsp"%>
</html>
