package com.example.restapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.util.regex.Pattern;
import java.util.Properties;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService = new UserService();
    public static final int NUMBER_OF_RANDOM_PASSWORD_CHARACTER = 6;

    @PostMapping("/add")
    public @ResponseBody ResponseEntity<?> postSignUp(@RequestParam String username, @RequestParam String email, @RequestParam String phoneNumber, @RequestParam String password, @RequestParam String fullName, @RequestParam String birthDay, @RequestParam boolean gender) {
        // check if the email is valid
        Pattern pattern = Pattern.compile(".+@gmail.com$");
        Matcher matcher = pattern.matcher(email);
        boolean isFound = matcher.find();
        if (!isFound) {
            return new ResponseEntity<>(new Detail("Email is invalid!"), HttpStatusCode.valueOf(600));
        }

        // check if the phone number is valid
        if (phoneNumber.length() != 10 || phoneNumber.charAt(0) != '0') {
            return new ResponseEntity<>(new Detail("Phone number is invalid!"), HttpStatusCode.valueOf(600));
        }

        // check if the username is in table
        if (userService.doesUsernameExist(username)) {
            return new ResponseEntity<>(new Detail("Username already exists!"), HttpStatusCode.valueOf(600));
        }

        // check if the email is registered
        if (userService.doesEmailExist(email)) {
            return new ResponseEntity<>(new Detail("Email already exists!"), HttpStatusCode.valueOf(600));
        }

        // check if the phoneNumber is registered
        if (userService.doesPhoneNumberExist(phoneNumber)) {
            return new ResponseEntity<>(new Detail("Phone number already exists!"), HttpStatusCode.valueOf(600));
        }

        // create new user
        User newUser = new User(username, email, phoneNumber, password, fullName, birthDay, gender);

        // add new user to the database
        userService.saveNewUser(newUser);

        return ResponseEntity.ok(new Detail("Sign up successfully!"));
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<?> postLogin(@RequestParam String username, @RequestParam String password) {
        var thisUser = userService.getUserById(username);

        if (!thisUser.isEmpty()) {
            if (password.equals(thisUser.get().getPassword())) {
                userService.updateIsActive(username, true);
                return new ResponseEntity<>(new Detail("Login successfully!"), HttpStatusCode.valueOf(200));
            } else {
                return new ResponseEntity<>(new Detail("Wrong password!"), HttpStatusCode.valueOf(600));
            }
        }

        return new ResponseEntity<>(new Detail("Username doesn't exist!"), HttpStatusCode.valueOf(600));
    }

    @PostMapping("/logout")
    public @ResponseBody ResponseEntity<?> postLogout(@RequestParam String username) {
        userService.updateIsActive(username, false);

        return ResponseEntity.ok(new Detail("Logout successfully!"));
    }

    @PostMapping("/password_recovery")
    public @ResponseBody ResponseEntity<?> postPasswordRecovery(@RequestParam String username, @RequestParam String email, @RequestParam String phoneNumber) {
        var thisUser = userService.getUserById(username);

        if (!thisUser.isEmpty()) {
            if (email.equals(thisUser.get().getEmail())) {
                if (phoneNumber.equals(thisUser.get().getPhoneNumber())) {
                    // generate new random password
                    String randomPassword = "";
                    for (int i = 0; i < NUMBER_OF_RANDOM_PASSWORD_CHARACTER; i++)
                    {
                        // 0: number, 1: uppercase character, 2: lowercase character
                        switch (userService.myRandom(0, 2)) {
                            case 0:
                                randomPassword += (char)userService.myRandom(48, 57);
                                break;
                            case 1:
                                randomPassword += (char)userService.myRandom(65, 90);
                                break;
                            case 2:
                                randomPassword += (char)userService.myRandom(97, 122);
                                break;
                        }
                    }

                    // send new password to user's email
                    Properties prop = new Properties();
                    prop.put("mail.smtp.auth", "true");
                    prop.put("mail.smtp.starttls.enable", "true");
                    prop.put("mail.smtp.port", "587");
                    prop.put("mail.smtp.host", "smtp.gmail.com");

                    Session session = Session.getInstance(prop, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("22521081@gm.uit.edu.vn", "mhzq ggqo ebrd ogxw");
                        }
                    });

                    try {
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("mailservice@gmail.com"));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                        message.setSubject("Docsavior Password Recovery");

                        String msg = "Your new password is: " + randomPassword;

                        MimeBodyPart mimeBodyPart = new MimeBodyPart();
                        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

                        Multipart multipart = new MimeMultipart();
                        multipart.addBodyPart(mimeBodyPart);

                        message.setContent(multipart);

                        Transport.send(message);
                    } catch (MessagingException ex) {
                        System.out.println(ex.getMessage());
                        return new ResponseEntity<>(new Detail(ex.getMessage()), HttpStatusCode.valueOf(500));
                    }

                    // update new password in database
                    userService.updatePassword(username, randomPassword);

                    return new ResponseEntity<>(new Detail("New password was sent to your email!"), HttpStatusCode.valueOf(200));
                } else {
                    return new ResponseEntity<>(new Detail("Wrong phone number!"), HttpStatusCode.valueOf(600));
                }
            } else {
                return new ResponseEntity<>(new Detail("Wrong email!"), HttpStatusCode.valueOf(600));
            }
        }

        return new ResponseEntity<>(new Detail("Username doesn't exist!"), HttpStatusCode.valueOf(600));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe(@RequestParam String username) {
        var user = userService.getUserById(username);

        if (user.isEmpty()) {
            return new ResponseEntity<>(new Detail("Username doesn't exist!"), HttpStatusCode.valueOf(600));
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", user.get().getUsername());
            jsonObject.put("email", user.get().getEmail());
            jsonObject.put("phoneNumber", user.get().getPhoneNumber());
            // jsonObject.put("password", user.get().getPassword());
            jsonObject.put("isActive", user.get().getIsActive());
            jsonObject.put("fullName", user.get().getFullName());
            jsonObject.put("birthDate", user.get().getBirthDate().toString());
            jsonObject.put("gender", user.get().getGender());
            jsonObject.put("avatarData", user.get().getAvatarData());
            jsonObject.put("avatarName", user.get().getAvatarName());
            jsonObject.put("avatarExtension", user.get().getAvatarExtension());

            return ResponseEntity.ok(jsonObject.toString());
        }
    }

    @PostMapping("/password_change")
    public ResponseEntity<?> postPasswordChange(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
        var user = userService.getUserById(username);

        if (!user.get().getPassword().equals(oldPassword)) {
            return new ResponseEntity<>(new Detail("Old password's wrong!"), HttpStatusCode.valueOf(600));
        }
        
        userService.updatePassword(username, newPassword);
        return ResponseEntity.ok(new Detail("Password changed successfully!"));
    }

    @PostMapping("/avatar")
    public ResponseEntity<?> postAvatar(@RequestParam String username, @RequestParam String avatarData, @RequestParam String avatarName, @RequestParam String avatarExtension) {
        userService.updateAvatar(username, avatarData, avatarName, avatarExtension);

        return ResponseEntity.ok(new Detail("Avatar updated successfully!"));
    }
    
}