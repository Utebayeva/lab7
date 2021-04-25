<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav>
    <a href="main.jsp">Main</a>
    <%if(session.getAttribute("username")!=null){ %>
        <a href="logout">Log out</a>
    <%}
    else{%>
        <a href="login.jsp">Login</a>
        <a href="registration.jsp">Register</a>
    <%}%>


</nav>
