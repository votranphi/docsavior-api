package com.example.restapi;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void updatePassword(String username, String password) {
        userRepository.findById(username).map(target -> {
            target.setPassword(password);
            return target; 
        });
    }

    public Optional<User> getUserById(String username) {
        return userRepository.findById(username);
    }

    public void saveNewUser(User newUser) {
        userRepository.save(newUser);
    }

    public boolean doesUsernameExist(String username) {
        return !userRepository.findById(username).isEmpty();
    }

    public boolean doesEmailExist(String email) {
        var allUsers = userRepository.findAll();

        for (User user : allUsers) {
            if (email.equals(user.getEmail())) {
                return true;
            }
        }

        return false;
    }

    public boolean doesPhoneNumberExist(String phoneNumber) {
        var allUsers = userRepository.findAll();

        for (User user : allUsers) {
            if (phoneNumber.equals(user.getPhoneNumber())) {
                return true;
            }
        }

        return false;
    }
}
