package com.example.restapi;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Newsfeed {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String username;

    private String postDescription;

    private String postContent;

    private Integer likeNumber;

    private Integer dislikeNumber;

    private Integer commentNumber;

    public Newsfeed(String username, String postDescription, String postContent, Integer likeNumber, Integer dislikeNumber, Integer commentNumber) {
        this.username = username;
        this.postDescription = postDescription;
        this.postContent = postContent;
        this.likeNumber = likeNumber;
        this.dislikeNumber = dislikeNumber;
        this.commentNumber = commentNumber;
    }

    public Newsfeed() { }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public String getPostContent() {
        return postContent;
    }

    public Integer getLikeNumber() {
        return likeNumber;
    }

    public Integer getDislikeNumber() {
        return dislikeNumber;
    }

    public Integer getCommentNumber() {
        return commentNumber;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public void setLikeNumber(Integer likeNumber) {
        this.likeNumber = likeNumber;
    }

    public void setDislikeNumber(Integer dislikeNumber) {
        this.dislikeNumber = dislikeNumber;
    }

    public void setCommentNumber(Integer commentNumber) {
        this.commentNumber = commentNumber;
    }
}
