package com.example.restapi;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer idComment;

    private String username;

    private Integer idPost; //idPost is the ID of newsfeed Entity

    private String commentContent;

    private Long time; // time in Unix

    public Comment(String username, Integer idPost, String commentContent)
    {
        this.idPost = idPost;
        this.username = username;
        this.commentContent = commentContent;
        // get the current time right after the object is created
        this.time = System.currentTimeMillis() / 1000L;
    }

    public Comment() { }

    public Integer getIdComment()
    {
        return idComment;
    }

    public String getUsername() {
        return username;
    }

    public Integer getIdPost()
    {
        return idPost;
    }

    public String getCommentContent()
    {
        return commentContent;
    }

    public Long getTime() {
        return time;
    }

    public void setIdComment(Integer idComment)
    {
        this.idComment = idComment;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIdPost(Integer idPost)
    {
        this.idPost = idPost;
    }

    public void setCommentContent(String commentContent)
    {
        this.commentContent = commentContent;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}