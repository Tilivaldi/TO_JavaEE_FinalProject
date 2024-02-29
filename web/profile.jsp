<%--
  Created by IntelliJ IDEA.
  User: Home
  Date: 28.02.2024
  Time: 0:51
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
            <h3 class="text-center">PROFILE PAGE OF <%=(currentUser!=null?currentUser.getFullName():"")%></h3>
        </div>
    </div>
</div>
<%@include file="footer.jsp"%>
</body>
<%@include file="foot.jsp"%>
</html>
