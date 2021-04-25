package com.example.forum.servlet;

import com.example.forum.database.Database;
import com.example.forum.entity.Comment;
import com.example.forum.entity.Post;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/post")
public class PostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = null;
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            username=(String) request.getSession(false).getAttribute("username");
        }

        int pId = Integer.parseInt(request.getParameter("pId"));
        Post post= Database.getPost(pId);
        request.setAttribute("userPost", false);
        if (username != null) {
            if (username == post.getUsername()){
                request.setAttribute("userPost", true);
            }
        }
        List<Comment> postComments = Database.getComments(pId);
        int likes= Database.getPostLikes(pId);
        int dislikes= Database.getPostDislikes(pId);

        request.setAttribute("post", post);
        request.setAttribute("comments", postComments);
        request.setAttribute("likes", likes);
        request.setAttribute("dislikes", dislikes);

        request.getRequestDispatcher("/post.jsp").forward(request, response);
    }

}
