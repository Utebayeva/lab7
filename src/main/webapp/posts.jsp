<%@ page import="com.example.forum.database.Database" %>
<%@ page import="com.example.forum.entity.Post" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<p class="message"><%=(request.getAttribute("message") == null) ? "" : request.getAttribute("message")%></p>

<h2>All Posts</h2>
<%
    if(session.getAttribute("username")!=null){
%>
<button><a href="writePost.jsp">Write your own post</a></button>
<%}

    List<Post> posts = Database.getPosts();
    if(posts != null){
        for(Post post : posts){
            int commentsNum = Database.getComments(post.getId()).size();
%>
        <div class="post">
            <h4><%=post.getUsername()%></h4>
            <h3><a href="post?pId=<%=post.getId()%>"><%=post.getTitle()%></a></h3>
            <p><%=post.getContent()%></p>
            <p><%=commentsNum%> comments</p>

            <%
                if(session.getAttribute("username")!=null){
                    if(session.getAttribute("username").equals(post.getUsername())){
            %>
            <button><a href="postEdit.jsp?pId=<%=post.getId()%>">Edit</a></button>
            <%
                    }
                }
            %>
        </div>

<%
        }
    }
%>
