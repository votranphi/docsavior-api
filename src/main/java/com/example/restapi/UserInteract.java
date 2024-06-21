package com.example.restapi;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserInteract {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer idInteract;
    
    private String username;
    
    private Integer idPost;

    private boolean type; // like is 1 and dislike is 0

    public UserInteract(String username, Integer idPost, Boolean type)
    {
        this.username = username;
        this.idPost = idPost;
        this.type = type;
    }

    public UserInteract() { }

    public String getUsername() {
        return username;
    }

    public Integer getIdPost() {
        return idPost;
    }

    public boolean getType()
    {
        return type;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setIdPost(Integer idPost)
    {
        this.idPost = idPost;
    }

    public void setType(boolean type)
    {
        this.type = type;
    }
}
