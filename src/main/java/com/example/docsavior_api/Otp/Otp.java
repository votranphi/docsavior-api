package com.example.docsavior_api.Otp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Otp {
    @Id
    private String username;

    private String otp;

    private Long createdTime; // created time in Unix

    private Long expiredTime; // expired time in Unix

    public Otp(String username, String otp, Long createdTime, Long expiredTime) {
        this.username = username;
        this.otp = otp;
        this.createdTime = createdTime;
        this.expiredTime = expiredTime;
    }

    public Otp() {};
    
    public String getUsername() {
        return username;
    }

    public String getOtp() {
        return otp;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }
}
