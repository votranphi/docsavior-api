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

    private String newsfeedFileData;

    private String newsfeedFileExtension;

    public Newsfeed(String username, String postDescription, String postContent, String newsfeedFileData, String newsfeedFileExtension) {
        this.username = username;
        this.postDescription = postDescription;
        this.postContent = postContent;
        this.likeNumber = 0;
        this.dislikeNumber = 0;
        this.commentNumber = 0;
        this.newsfeedFileData = newsfeedFileData;
        this.newsfeedFileExtension = newsfeedFileExtension;
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

    public String getNewsfeedFileData() {
        return newsfeedFileData;
    }

    public String getNewsfeedFileExtension() {
        return newsfeedFileExtension;
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
    
    public void setNewsfeedFileData(String newsfeedFileData) {
        this.newsfeedFileData = newsfeedFileData;
    }

    public void setNewsfeedFileExtension(String newsfeedFileExtension) {
        this.newsfeedFileExtension = newsfeedFileExtension;
    }
}
