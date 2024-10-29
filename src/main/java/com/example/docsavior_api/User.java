package com.example.docsavior_api;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class User {
    @Id
    private String username;

    private String email;

    private String phoneNumber;

    private String password;

    private boolean isActive;

    private String fullName;

    private String birthDate;

    private boolean gender; // male is 1 and female is 0

    // Two annotations below set the column's data type is LONGTEXT (store up to 4GB string)
    @Lob @Column(length = 16777216)
    private String avatarData;

    private String avatarName;
 
    private String avatarExtension;

    public User(String username, String email, String phoneNumber, String password, String fullName, String birthDate, boolean gender) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password; 
        this.isActive = false;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.avatarData = "";
        this.avatarName = "";
        this.avatarExtension = "";
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

    public String getFullName() {
        return fullName;
    }
    
    public String getBirthDate() {
        return birthDate;
    }

    public boolean getGender() {
        return gender;
    }

    public String getAvatarData() {
        return avatarData;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public String getAvatarExtension() {
        return avatarExtension;
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

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setAvatarData(String avatarData) {
        this.avatarData = avatarData;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public void setAvatarExtension(String avatarExtension) {
        this.avatarExtension = avatarExtension;
    }
}
