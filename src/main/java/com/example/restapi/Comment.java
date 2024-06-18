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

    private Integer idPost; //idPost is the ID of newsfeed Entity

    private String commentContent;

    public Comment(Integer idPost, String commentContent)
    {
        this.idPost = idPost;
        this.commentContent = commentContent;
    }

    public Comment() { }

    public Integer getIdComment()
    {
        return idComment;
    }

    public Integer getIdPost()
    {
        return idPost;
    }

    public String getCommentContent()
    {
        return commentContent;
    }

    public void setIdComment(Integer idComment)
    {
        this.idComment = idComment;
    }

    public void setIdPost(Integer idPost)
    {
        this.idPost = idPost;
    }

    public void setCommentContent(String commentContent)
    {
        this.commentContent = commentContent;
    }
}