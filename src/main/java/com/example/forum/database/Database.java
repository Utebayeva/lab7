package com.example.forum.database;

import com.example.forum.entity.Comment;
import com.example.forum.entity.Post;
import com.example.forum.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static String url = "jdbc:mysql://localhost/forum";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String login = "root";
    private static String password = "root";
    static Connection connection;
    static Statement statement;

    public static void connect() {
        if (connection == null) {
            try {
                Class.forName(driverName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                connection = DriverManager.getConnection(url, login, password);
                statement = connection.createStatement();
            } catch (SQLException e) {
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static User getUser(String username, String password){
        connect();

        String sqlString = "SELECT * FROM users WHERE username='" + username +"' AND "+"password='" + password+"'";
        User user = new User();
        try {
            ResultSet resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()) {
                user.setId(resultSet.getInt("userId"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
            }
            } catch (SQLException e) {
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

        return user;
    }

    public static User getUserById(int userId){
        connect();

        String sqlString = "SELECT * FROM users WHERE user_id=" + userId;
        User user = new User();
        try {
            ResultSet resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()) {
                user.setId(resultSet.getInt("userId"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public static int createUser(User user){
        connect();

        String sqlString = "insert into users (email, username, password) values('"
                +user.getEmail()+"', '"
                +user.getUsername()+"', '"
                +user.getPassword()+"')";

        int result=0;
        try{
            result = statement.executeUpdate(sqlString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static List<User> getUsers(){
        connect();

        String sqlString = "SELECT * FROM users";
        List<User> userList = new ArrayList<User>();
        try {
            ResultSet resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()) {
                User user=new User();
                user.setId(resultSet.getInt("userId"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                userList.add(user);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }


    public static List<Post> getPosts(){
        connect();

        String sqlString = "SELECT * FROM posts";
        List<Post> posts = new ArrayList<Post>();
        try {
            ResultSet resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()) {
                Post post =new Post();
                post.setId(resultSet.getInt("pId"));
                post.setTitle(resultSet.getString("title"));
                post.setContent(resultSet.getString("pContent"));
                post.setUsername(resultSet.getString("username"));
                posts.add(post);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return posts;
    }


    public static List<Comment> getComments(int pId){
        connect();

        String sqlString = "SELECT * FROM comments where pid="+pId;
        List<Comment> comments = new ArrayList<Comment>();
        try {
            ResultSet resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()) {
                Comment comment =new Comment();
                comment.setId(resultSet.getInt("cId"));
                comment.setContent(resultSet.getString("content"));
                comment.setPostId(resultSet.getInt("pId"));
                comment.setUsername(resultSet.getString("username"));;
                comments.add(comment);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return comments;
    }

    public static Post getPost(int pId){
        connect();

        String sqlString = "SELECT * FROM posts where pId="+pId;
        Post post =new Post();
        try {
            ResultSet resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()) {

                post.setId(resultSet.getInt("pId"));
                post.setTitle(resultSet.getString("title"));
                post.setContent(resultSet.getString("pContent"));
                post.setUsername(resultSet.getString("username"));
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return post;
    }

    public static int getPostLikes(int pId){
        connect();

        String sqlString = "SELECT count(*) as likes FROM post_likes where type='like' pid="+pId;
        int likes=0;
        try {
            ResultSet resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()) {
                likes = resultSet.getInt("likes");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return likes;
    }

    public static int getPostDislikes(int pId){
        connect();

        String sqlString = "SELECT count(*) as dislike FROM post_likes where type='dislike' pid="+pId;
        int dislike=0;
        try {
            ResultSet resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()) {
                dislike = resultSet.getInt("dislike");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dislike;
    }

    public static int getCommentLikes(int cId){
        connect();

        String sqlString = "SELECT count(*) as likes FROM comment_likes where type='like' cId="+cId;
        int likes=0;
        try {
            ResultSet resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()) {
                likes = resultSet.getInt("likes");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return likes;
    }

    public static int getCommentDislikes(int cId){
        connect();

        String sqlString = "SELECT count(*) as dislike FROM comment_likes where type='dislike' cId="+cId;
        int dislike=0;
        try {
            ResultSet resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()) {
                dislike = resultSet.getInt("dislike");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dislike;
    }


    public static int createPost(Post post){

        String sqlString = "INSERT INTO `forum`.`posts` (`title`, `pContent`, `username`) values('"
                +post.getTitle()+"', '"
                +post.getContent()+"', '"
                +post.getUsername()+"')";

        int result=0;
        try{
            result = statement.executeUpdate(sqlString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static int createComment(Comment comment){

        String sqlString = "INSERT INTO `forum`.`comments` (`content`, `pId`, `username`) values('"
                +comment.getContent()+"', "
                +comment.getPostId()+", '"
                +comment.getUsername()+"')";

        int result=0;
        try{
            result = statement.executeUpdate(sqlString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static int updatePost(Post post){

        String sqlString = "UPDATE `forum`.`posts` SET" +
                " title = '"+post.getTitle()+"'," +
                " pContent = '"+post.getContent()+"'" +
                " WHERE pId = "+post.getId()+"";

        int result=0;
        try{
            result = statement.executeUpdate(sqlString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }



}
