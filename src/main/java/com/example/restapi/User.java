package com.example.restapi;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    private String username;

    private String email;

    private String phoneNumber;

    private String password;

    private boolean isActive;

    public User(String username, String email, String phoneNumber, String password, boolean isActive) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.isActive = isActive;
    }

    public User() { }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
