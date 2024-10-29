package com.example.docsavior_api;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String username;

    private String sender;

    private String content;

    private boolean isSeen;

    private Long time;

    public Message(String username, String sender, String content) {
        this.username = username;
        this.sender = sender;
        this.content = content;
        this.isSeen = false;
        this.time = System.currentTimeMillis() / 1000L;
    }

    public Message() {
        
    }

    public Integer getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public Long getTime() {
        return time;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public boolean getIsSeen() {
        return isSeen;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIsSeen(boolean isSeen) {
        this.isSeen = isSeen;
    }
}
