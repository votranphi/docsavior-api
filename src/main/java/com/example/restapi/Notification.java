package com.example.restapi;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer idNotification;

    private String time;

    private String type; // "like" or "comment"

    private Integer idAddFriend; // can NULL

    private Integer idPost; // can NULL


    public Notification(String time, String type, Integer idAddFriend, Integer idPost)
    {
        this.time = time;
        this.type = type;
        this.idAddFriend = idAddFriend;
        this.idPost = idPost;
    }

    public Notification() { }

    public Integer getIdNotification()
    {
        return idNotification;
    }

    public String getTime()
    {
        return time;
    }

    public String getType() {
        return type;
    }

    public Integer getIdAddFriend()
    {
        return idAddFriend;
    }

    public Integer getIdPost()
    {
        return idPost;
    }

    public void setIdNotification(Integer idNotification)
    {
        this.idNotification = idNotification;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIdAddFriend(Integer idAddFriend)
    {
        this.idAddFriend = idAddFriend;
    }

    public void setIdPost(Integer idPost)
    {
        this.idPost = idPost;
    }

     
}