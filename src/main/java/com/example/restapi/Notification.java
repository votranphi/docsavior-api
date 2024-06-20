package com.example.restapi;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer idNotification;// Notification's id

    private String username; //username of this account

    private Integer type; // like=0, comment=1, friendRequest=2

    private Integer idPost;// post's id. This can be NULL if type is 2

    private String userInteract; // username of the person who interact with post or send a request


    public Notification(String username, Integer type,  Integer idPost, String userInteract)
    {
        this.username = username;
        this.type = type;
        this.idPost = idPost;
        this.userInteract = userInteract;
    }

    public Notification(String username, Integer type, String userInteract)
    {
        this.username = username;
        this.type = type;
        this.userInteract = userInteract;
    }

    public Notification() { }


    public Integer getIdNotification()
    {
        return idNotification;
    }

    public String getUsername()
    {
        return username;
    }

    public Integer getType() {
        return type;
    }

    public Integer getIdPost()
    {
        return idPost;
    }

    public String getUserInteract()
    {
        return userInteract;
    }

    public void setIdNotification(Integer idNotification)
    {
        this.idNotification = idNotification;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setIdPost(Integer idPost)
    {
        this.idPost = idPost;
    }

    public void setUserInteract(String userInteract)
    {
        this.userInteract = userInteract;
    }

     
}