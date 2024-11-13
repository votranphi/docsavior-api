package com.example.docsavior_api.Notification;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer idNotification; // Notification's id

    private String username; // username of the account which is the notification owner

    private Integer type; // like = 0, dislike = 1, comment = 2, friend request = 3, accept friend request = 4, reject friend request = 5

    private Integer idPost; // post's id. This can be -1 if type is 3 or 4 or 5

    private String interacter; // username of the person who interact with post or send a request

    private String notificationContent; // "has liked/disliked your post", "has left a comment on your post", "has sent you a friend request"

    private Long time; // in Unix

    public Notification(String username, Integer type,  Integer idPost, String interacter)
    {
        this.username = username;
        this.type = type;
        this.idPost = idPost;
        this.interacter = interacter;
        
        if (type == 0) {
            this.notificationContent = "has liked your post";
        } else if (type == 1) {
            this.notificationContent = "has disliked your post";
        } else if (type == 2) {
            this.notificationContent = "has left a comment on your post";
        } else if (type == 3) {
            this.notificationContent = "has sent you a friend request";
        } else if (type == 4) {
            this.notificationContent = "has accepted your friend request";
        } else {
            this.notificationContent = "has rejected your friend request";
        }

        this.time = System.currentTimeMillis() / 1000L;
    }

    public Notification() { }

    public Integer getIdNotification() {
        return idNotification;
    }

    public Integer getIdPost() {
        return idPost;
    }

    public String getInteracter() {
        return interacter;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public Long getTime() {
        return time;
    }

    public Integer getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public void setIdNotification(Integer idNotification) {
        this.idNotification = idNotification;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }
    
    public void setInteracter(String interacter) {
        this.interacter = interacter;
    }
    
    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}