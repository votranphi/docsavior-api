package com.example.docsavior_api;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

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

    @Transactional
    public void updateUserInfo(String username, String fullName, String email, boolean gender, String birthDate, String phoneNumber)
    {
        userRepository.findById(username).map(target -> {
            target.setFullName(fullName);
            target.setEmail(email);
            target.setGender(gender);
            target.setBirthDate(birthDate);
            target.setPhoneNumber(phoneNumber);
            return target;
        });
    }
    // function to get avatarData by username
    public String getAvatarDataByUsername(String username) {
        return userRepository.findAvatarDataByUsername(username);
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
        return userRepository.findById(username).isPresent();
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

    // function to findAll in database
    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    // funtion to random an interger between min and max
    public int myRandom(int min, int max) {
        return (int)(ThreadLocalRandom.current().nextDouble() * (max - min + 0.99) + min);
    }

    // function to get user's status by username
    public boolean getStatusByUsername(String username) {
        return userRepository.findStatusByUsername(username);
    }

    // function to get password recovery html
    public String getPassRecoHtml() {
        return "<!DOCTYPE html>\r\n" + //
                        "<html lang=\"en\">\r\n" + //
                        "<head>\r\n" + //
                        "    <meta charset=\"UTF-8\">\r\n" + //
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + //
                        "    <title>Email Verification</title>\r\n" + //
                        "</head>\r\n" + //
                        "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; display: flex; justify-content: center; align-items: center; height: fit-content;\">\r\n" + //
                        "    <div style=\"background-color: white;padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); max-width: 600px; text-align: center; margin: 10px auto 10px auto; height: fit-content;\">\r\n" + //
                        "        <header>\r\n" + //
                        "            <img src=\"https://res.cloudinary.com/diy6oyo6l/image/upload/v1719236100/Temporary_ahedik.png\" alt=\"Logo\" style=\"width: 80px; margin-bottom: 20px;\">\r\n" + //
                        "            <h2>Docsavior</h2>\r\n" + //
                        "        </header>\r\n" + //
                        "        <main>\r\n" + //
                        "            <h1 style=\"color: #4a90e2; margin-bottom: 20px;\">Password Recovery</h1>\r\n" + //
                        "            <p style=\"color: #333; line-height: 1.6; margin-bottom: 20px;\">Hello <b>{username}</b>,</p>\r\n" + //
                        "            <p style=\"color: #333; line-height: 1.6; margin-bottom: 20px;\">Here's your new password for Docsavior application.</p>\r\n" + //
                        "            <p style=\"color: #333; line-height: 1.6; margin-bottom: 20px;\">Your new password:</p>\r\n" + //
                        "            <div style=\"font-size: 32px; font-weight: bold; color: #333; margin-bottom: 20px;\" id=\"otp-code\">{new_password}</div>\r\n" + //
                        "        </main>\r\n" + //
                        "        <footer>\r\n" + //
                        "            <p style=\"font-size: 14px; color: #888; margin: 10px 0;\">If this wasn't you, please ignore this email or contact our customer service center: <a href=\"mailto:docsavior.service@gmail.com\" style=\"color: #4a90e2; text-decoration: underline; margin: 0 5px;\">docsavior.service@gmail.com</a> for further assistance.</p>\r\n" + //
                        "        </footer>\r\n" + //
                        "    </div>\r\n" + //
                        "</body>\r\n" + //
                        "</html>";
    }
}
