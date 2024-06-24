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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value = "/otp")
public class OtpController {
    @Autowired
    OtpService otpService = new OtpService();

    @PostMapping("/create_or_refresh")
    public ResponseEntity<?> postCreateOrRefresh(@RequestParam String username, @RequestParam String email) {
        Optional<Otp> otp = otpService.getOtpById(username);

        if (otp.isEmpty()) {
            otpService.createOtp(username);
        } else {
            if (otpService.isOtpExpired(username)) {
                otpService.refreshOtp(username);
            }
        }

        otp = otpService.getOtpById(username);

        // send otp password to user's email
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
            message.setSubject("OTP Code: " + otp.get().getOtp());

            Path path = Paths.get("src\\main\\resources\\otp.html");

            String msgBody = Files.readString(path);

            msgBody.replace("{username}", username);
            msgBody.replace("{otp_code}", otp.get().getOtp());

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msgBody, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(new Detail(ex.getMessage()), HttpStatusCode.valueOf(500));
        }

        return ResponseEntity.ok(new Detail("Send OTP to email successfully!"));
    }

    @PostMapping("/check")
    public ResponseEntity<?> postCheckOtp(@RequestParam String username, @RequestParam String otp) {
        if (otpService.isOtpCorrect(username, otp)) {
            if (otpService.isOtpExpired(username)) {
                return new ResponseEntity<>(new Detail("OTP expired!"), HttpStatusCode.valueOf(600)); // code 600: OTP Expired
            } else {
                return ResponseEntity.ok(new Detail("OTP is correct!"));
            }
        } else {
            return new ResponseEntity<>(new Detail("OTP is not correct!"), HttpStatusCode.valueOf(601)); // code 601: OTP it not correct
        }
    }
    
}
