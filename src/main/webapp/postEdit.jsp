<%@ page import="com.example.forum.database.Database" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="post" class="com.example.forum.entity.Post"/>
<jsp:setProperty property="*" name="post"/>

<%
    String message=null;
    int pId=post.getId();
    if(request.getParameter("title")!=null && request.getParameter("pContent")!=null){
        int status=Database.updatePost(post);
        if(status>0)
            message="You are successfully edit post";
    }else{
        post=Database.getPost(pId);
        %>
        <jsp:setProperty property="title" name="post" value="<%=post.getTitle()%>"/>
        <jsp:setProperty property="pContent" name="post" value="<%=post.getId()%>"/>
<%}%>
<html>
<head>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Post</title>
    </head>
</head>
<%@ include file="header.jsp" %>
<body>
<div class="post">
    <h3>Title: <jsp:getProperty property="title" name="post" /></h3>
    <p>Content:<jsp:getProperty property="pContent" name="post"/></p>
    <form action="/postEdit.jsp" method="POST">
        <p> <%=(message == null) ? "": message%></p>
        <h2>Edit post</h2>
        <label>Title:</label><br>
        <input  type="text" name="title" value="<jsp:getProperty property="title" name="post"/>"><br><br>
        <label>Content: </label><br>
        <textarea name="pContent"><jsp:getProperty property="pContent" name="post"/></textarea><br><br>
        <input type="hidden" name="pId" value="<jsp:getProperty property="pId" name="post"/>"><br><br>
        <button>Republish</button>
    </form>

</div>
</body>
</html>
