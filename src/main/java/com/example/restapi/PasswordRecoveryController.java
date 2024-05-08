package com.example.restapi;

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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping(value = "/api")
public class PasswordRecoveryController {
    public static final int NUMBER_OF_RANDOM_PASSWORD_CHARACTER = 6;

    @PostMapping("/password_recovery")
    public ResponseEntity<?> postPasswordRecovery(@RequestParam String username, @RequestParam String email, @RequestParam String phoneNumber) {
        // read all the text in UsersAccount.json to jsonString
        Path path = Paths.get("src\\main\\resources\\UserAccounts.json");
        String jsonString = "";
        try {
            jsonString = Files.readString(path);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        // convert jsonString to jsonArray
        JSONArray jsonArray = new JSONArray(jsonString);

        // check if the username is in the jsonObject, the password is correct
        for (Object object : jsonArray) {
            JSONObject temp = (JSONObject)object;

            // check if the username is existed, the email and the phone number is correct
            if (username.equals((String)temp.get("username"))) {
                if (email.equals((String)temp.getString("email"))) {
                    if (phoneNumber.equals((String)temp.get("phoneNumber"))) {
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
                        // prop.put("mail.smtp.host", "smtp.mailtrap.io");
                        prop.put("mail.smtp.port", "587");
                        // prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
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
                        
                        // set the password in database to a new one
                        temp.remove("password");
                        temp.put("password", randomPassword);

                        // write the jsonArray to the json file again
                        try {
                            PrintWriter printWriter = new PrintWriter("src\\main\\resources\\UserAccounts.json");
                            printWriter.println(jsonArray.toString());
                            printWriter.close();
                        } catch (IOException ex) {
                            System.out.println("File not found!");
                        }

                        return ResponseEntity.ok(new Detail("Password was sent to your email!"));
                    } else {
                        return new ResponseEntity<>(new Detail("Wrong phone number!"), HttpStatusCode.valueOf(401));
                    }
                } else {
                    return new ResponseEntity<>(new Detail("Wrong email!"), HttpStatusCode.valueOf(401));
                }
            }
        }

        return new ResponseEntity<>(new Detail("Username doesn't exist!"), HttpStatusCode.valueOf(401));
    }

    private int myRandom(int min, int max) {
        return (int)(Math.random() * (max - min + 0.99) + min);
    }
}
