package com.example.docsavior_api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
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
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService = new UserService();
    public static final int NUMBER_OF_RANDOM_PASSWORD_CHARACTER = 6;

    @PostMapping("/add")
    public @ResponseBody ResponseEntity<Detail> postSignUp(@RequestParam String username, @RequestParam String email, @RequestParam String phoneNumber, @RequestParam String password, @RequestParam String fullName, @RequestParam String birthDay, @RequestParam boolean gender) {
        // create new user
        User newUser = new User(username, email, phoneNumber, password, fullName, birthDay, gender);

        // add new user to the database
        userService.saveNewUser(newUser);

        return ResponseEntity.ok(new Detail("Sign up successfully!"));
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Detail> postLogin(@RequestParam String username, @RequestParam String password) {
        var thisUser = userService.getUserById(username);

        if (thisUser.isPresent()) {
            if (password.equals(thisUser.get().getPassword())) {
                return new ResponseEntity<Detail>(new Detail("Login successfully!"), HttpStatusCode.valueOf(200));
            } else {
                return new ResponseEntity<Detail>(new Detail("Wrong password!"), HttpStatusCode.valueOf(600));
            }
        }

        return new ResponseEntity<>(new Detail("Username doesn't exist!"), HttpStatusCode.valueOf(600));
    }

    @PostMapping("/logout")
    public @ResponseBody ResponseEntity<Detail> postLogout(@RequestParam String username) {
        userService.updateIsActive(username, false);

        return ResponseEntity.ok(new Detail("Logout successfully!"));
    }

    @PostMapping("/login_to_true")
    public @ResponseBody ResponseEntity<Detail> postLoginSignal(@RequestParam String username) {
        userService.updateIsActive(username, true);

        return ResponseEntity.ok(new Detail("Login successfully!"));
    }

    @PostMapping("/password_recovery")
    public @ResponseBody ResponseEntity<Detail> postPasswordRecovery(@RequestParam String username, @RequestParam String email, @RequestParam String phoneNumber) {
        var thisUser = userService.getUserById(username);

        if (thisUser.isPresent()) {
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
                    // prop.put("mail.smtp.starttls.enable", "true");
                    // prop.put("mail.smtp.port", "587");
                    prop.put("mail.smtp.host", "smtp.gmail.com");
                    prop.put("mail.smtp.socketFactory.port", "465");
                    prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

                    Session session = Session.getInstance(prop, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("docsavior.service@gmail.com", "fukp iopw jkzb bptp");
                        }
                    });

                    try {
                        MimeMessage message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("docsavior.service@gmail.com", "Docsavior"));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                        message.setSubject("New password: " + randomPassword);

                        String msgBody = userService.getPassRecoHtml();

                        msgBody = msgBody.replace("{username}", username);
                        msgBody = msgBody.replace("{new_password}", randomPassword);

                        MimeBodyPart mimeBodyPart = new MimeBodyPart();
                        mimeBodyPart.setContent(msgBody, "text/html; charset=utf-8");

                        Multipart multipart = new MimeMultipart();
                        multipart.addBodyPart(mimeBodyPart);

                        message.setContent(multipart);

                        Transport.send(message);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        return new ResponseEntity<Detail>(new Detail(ex.getMessage()), HttpStatusCode.valueOf(500));
                    }

                    // update new password in database
                    userService.updatePassword(username, randomPassword);

                    return new ResponseEntity<Detail>(new Detail("New password was sent to your email!"), HttpStatusCode.valueOf(200));
                } else {
                    return new ResponseEntity<Detail>(new Detail("Wrong phone number!"), HttpStatusCode.valueOf(600));
                }
            } else {
                return new ResponseEntity<Detail>(new Detail("Wrong email!"), HttpStatusCode.valueOf(600));
            }
        }

        return new ResponseEntity<Detail>(new Detail("Username doesn't exist!"), HttpStatusCode.valueOf(600));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe(@RequestParam String username) {
        var user = userService.getUserById(username);

        if (user.isEmpty()) {
            return new ResponseEntity<>(new Detail("Username doesn't exist!"), HttpStatusCode.valueOf(600));
        } else {
            return ResponseEntity.ok(user.get());
        }
    }

    @PostMapping("/password_change")
    public ResponseEntity<Detail> postPasswordChange(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
        var user = userService.getUserById(username);

        if (!user.get().getPassword().equals(oldPassword)) {
            return new ResponseEntity<Detail>(new Detail("Old password's wrong!"), HttpStatusCode.valueOf(600));
        }
        
        userService.updatePassword(username, newPassword);
        return ResponseEntity.ok(new Detail("Password changed successfully!"));
    }

    @PostMapping("/avatar")
    public ResponseEntity<Detail> postAvatar(@RequestBody User user) {
        userService.updateAvatar(user.getUsername(), user.getAvatarData(), user.getAvatarName(), user.getAvatarExtension());

        return ResponseEntity.ok(new Detail("Avatar updated successfully!"));
    }
    
    @PostMapping("/look_up")
    public ResponseEntity<List<User>> postLookUpUser(@RequestParam String lookUpInfo) {
        // get all users in database
        Iterable<User> users = userService.findAllUsers();

        // lowercase the lookUpInfo and remove the leading/trailing space if it has
        String lowercasedLKI = lookUpInfo.toLowerCase().trim();

        // split the string to do the searching
        String[] spl = lowercasedLKI.split("\s");

        // init the foundUsers which stores found users
        List<User> foundUsers = new ArrayList<>();

        // found user
        for (User i : users) {
            for (String j : spl) {
                // add the user if it's contains at least one word of the lookUpInfo
                if (i.getUsername().toLowerCase().contains(j) || i.getFullName().toLowerCase().contains(j)) {
                    foundUsers.add(i);
                    break;
                }
            }
        }

        return ResponseEntity.ok(foundUsers);
    }
    
    @GetMapping("/avatar_data")
    public ResponseEntity<Detail> getMethodName(@RequestParam String username) {
        return ResponseEntity.ok(new Detail(userService.getAvatarDataByUsername(username)));
    }
    
    @PostMapping("/update_user_info")
    public ResponseEntity<Detail> postUserInfo(@RequestParam String username, @RequestParam String fullName, @RequestParam String email, @RequestParam boolean gender, @RequestParam String birthDate, @RequestParam String phoneNumber)
    {
        userService.updateUserInfo(username, fullName, email, gender, birthDate, phoneNumber);
        return ResponseEntity.ok(new Detail("update user information succesfully!"));
    }

    @GetMapping("/status")
    public ResponseEntity<Detail> getUserStatus(@RequestParam String username) {
        boolean userStatus = userService.getStatusByUsername(username);

        return ResponseEntity.ok(new Detail(userStatus ? "true" : "false"));
    }

    @PostMapping("/check")
    public @ResponseBody ResponseEntity<Detail> postCheckSignUpInfo(@RequestParam String username, @RequestParam String email, @RequestParam String phoneNumber) {
        // check if the email is valid
        Pattern pattern = Pattern.compile(".+@gmail.com$");
        Matcher matcher = pattern.matcher(email);
        boolean isFound = matcher.find();
        if (!isFound) {
            return new ResponseEntity<Detail>(new Detail("Email is invalid!"), HttpStatusCode.valueOf(600));
        }

        // check if the phone number is valid
        if (phoneNumber.length() != 10 || phoneNumber.charAt(0) != '0') {
            return new ResponseEntity<Detail>(new Detail("Phone number is invalid!"), HttpStatusCode.valueOf(600));
        }

        // check if the username is in table
        if (userService.doesUsernameExist(username)) {
            return new ResponseEntity<Detail>(new Detail("Username already exists!"), HttpStatusCode.valueOf(600));
        }

        // check if the email is registered
        if (userService.doesEmailExist(email)) {
            return new ResponseEntity<Detail>(new Detail("Email already exists!"), HttpStatusCode.valueOf(600));
        }

        // check if the phoneNumber is registered
        if (userService.doesPhoneNumberExist(phoneNumber)) {
            return new ResponseEntity<Detail>(new Detail("Phone number already exists!"), HttpStatusCode.valueOf(600));
        }

        return ResponseEntity.ok(new Detail("Username, email and phone number are valid!"));
    }
}