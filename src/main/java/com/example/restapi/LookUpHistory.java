package com.example.restapi;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class LookUpHistory {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String username;

    private String lookUpInfo;

    private Integer lookUpType; // 0 is lookup in newsfeed, 1 is look up in chat, 2 is lookup in friend

    private Long time; // time in Unix

    public LookUpHistory(String username, String lookUpInfo, Integer lookUpType)
    {
        this.username = username;
        this.lookUpInfo = lookUpInfo;
        this.lookUpType = lookUpType;
        this.time = System.currentTimeMillis() / 1000L;
    }

    public LookUpHistory() { }

    public Integer getId() {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public String getLookUpInfo() {
        return lookUpInfo;
    }

    public Integer getLookUpType() {
        return lookUpType;
    }

    public Long getTime() {
        return time;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setLookUpInfo(String lookUpInfo)
    {
        this.lookUpInfo = lookUpInfo;
    }

    public void setLookUpType(Integer lookUpType) {
        this.lookUpType = lookUpType;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}