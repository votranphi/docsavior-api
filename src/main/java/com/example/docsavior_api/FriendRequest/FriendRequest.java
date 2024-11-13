package com.example.docsavior_api.FriendRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FriendRequest {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    private String username;
    
    private String requester;

    public FriendRequest(String username, String requester)
    {
        this.username = username;
        this.requester = requester;
    }

    public FriendRequest() { }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRequester() {
        return requester;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }
}
