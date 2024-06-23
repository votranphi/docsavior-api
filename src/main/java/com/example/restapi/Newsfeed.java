package com.example.restapi;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

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

    // Two annotations below set the column's data type is LONGTEXT (store up to 4GB string)
    @Lob @Column(length = 16777216)
    private String fileData;

    private String fileName;
 
    private String fileExtension;

    private Long time;

    public Newsfeed(String username, String postDescription, String postContent, String fileData, String fileName, String fileExtension) {
        this.username = username;
        this.postDescription = postDescription;
        this.postContent = postContent;
        this.likeNumber = 0;
        this.dislikeNumber = 0;
        this.commentNumber = 0;
        this.fileData = fileData;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.time = System.currentTimeMillis() / 1000L;
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

    public String getFileData() {
        return fileData;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public Long getTime() {
        return time;
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
    
    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
