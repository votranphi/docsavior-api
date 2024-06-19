package com.example.restapi;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LookUpHistory {
    @Id
    private String username;

    private String lookUpInfo;

    public LookUpHistory(String username, String lookUpInfo)
    {
        this.username = username;
        this.lookUpInfo = lookUpInfo;
    }

    public LookUpHistory() { }

    public String getUsername()
    {
        return username;
    }

    public String lookUpInfo()
    {
        return lookUpInfo;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setLookUpInfo(String lookUpInfo)
    {
        this.lookUpInfo = lookUpInfo;
    }
}