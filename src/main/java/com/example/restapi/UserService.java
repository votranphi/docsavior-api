package com.example.restapi;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // function to do updating password to the User table
    @Transactional
    public void updatePassword(String username, String password) {
        userRepository.findById(username).map(target -> {
            target.setPassword(password);
            return target; 
        });
    }

    // function to do updating active status to the User table
    @Transactional
    public void updateIsActive(String username, boolean isActive) {
        userRepository.findById(username).map(target -> {
            target.setIsActive(isActive);;
            return target; 
        });
    }

    // function to do updating avatar to the User table
    @Transactional
    public void updateAvatar(String username, String avatarData, String avatarName, String avatarExtension) {
        userRepository.findById(username).map(target -> {
            target.setAvatarData(avatarData);
            target.setAvatarName(avatarName);
            target.setAvatarExtension(avatarExtension);
            return target;
        });
    }

    // function to get user base on it's id
    public Optional<User> getUserById(String username) {
        return userRepository.findById(username);
    }

    // function to save new user to User table
    public void saveNewUser(User newUser) {
        userRepository.save(newUser);
    }

    // function to check if the username is in the User table
    public boolean doesUsernameExist(String username) {
        return !userRepository.findById(username).isEmpty();
    }

    // function to check if the email is in the User table
    public boolean doesEmailExist(String email) {
        var allUsers = userRepository.findAll();

        for (User user : allUsers) {
            if (email.equals(user.getEmail())) {
                return true;
            }
        }

        return false;
    }

    // function to check if the phoneNumber is in the User table
    public boolean doesPhoneNumberExist(String phoneNumber) {
        var allUsers = userRepository.findAll();

        for (User user : allUsers) {
            if (phoneNumber.equals(user.getPhoneNumber())) {
                return true;
            }
        }

        return false;
    }

    // funtion to random an interger between min and max
    public int myRandom(int min, int max) {
        return (int)(Math.random() * (max - min + 0.99) + min);
    }
}
