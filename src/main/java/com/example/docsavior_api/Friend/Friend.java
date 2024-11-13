package com.example.docsavior_api.Friend;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Friend {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String username;

    private String usernameFriend;

    public Friend (String username, String usernameFriend)
    {
        this.username = username;
        this.usernameFriend = usernameFriend;
    }

    public Friend() { }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getUsernameFriend() {
        return usernameFriend;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsernameFriend(String usernameFriend) {
        this.usernameFriend = usernameFriend;
    }
}