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

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService = new UserService();

    @PostMapping("/add")
    public @ResponseBody ResponseEntity<?> postSignUp(@RequestParam String username, @RequestParam String email, @RequestParam String phoneNumber, @RequestParam String password) {
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

        // create new user
        User newUser = new User(username, email, phoneNumber, password, false);

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

        // add new user to the database
        userService.saveNewUser(newUser);

        return ResponseEntity.ok(new Detail("Sign up successfully!"));
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<?> postLogin(@RequestParam String username, @RequestParam String password) {
        var thisUser = userService.getUserById(username);

        if (!thisUser.isEmpty()) {
            if (password.equals(thisUser.get().getPassword())) {
                return new ResponseEntity<>(new Detail("Login successfully!"), HttpStatusCode.valueOf(200));
            } else {
                return new ResponseEntity<>(new Detail("Wrong password!"), HttpStatusCode.valueOf(600));
            }
        }

        return new ResponseEntity<>(new Detail("Username doesn't exist!"), HttpStatusCode.valueOf(600));
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
                        switch (myRandom(0, 2)) {
                            case 0:
                                randomPassword += (char)myRandom(48, 57);
                                break;
                            case 1:
                                randomPassword += (char)myRandom(65, 90);
                                break;
                            case 2:
                                randomPassword += (char)myRandom(97, 122);
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

    public static final int NUMBER_OF_RANDOM_PASSWORD_CHARACTER = 6;
    private int myRandom(int min, int max) {
        return (int)(Math.random() * (max - min + 0.99) + min);
    }
}