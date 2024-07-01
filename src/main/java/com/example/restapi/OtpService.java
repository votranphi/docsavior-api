package com.example.restapi;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class OtpService {
    @Autowired
    OtpRepository otpRepository;

    private final long EXPIRED_DURATION_IN_SECOND = 300; // 5 minutes
    private final int NO_OTP_DIGIT = 4; // 4 - digit otp

    // function to get otp base on username
    public Optional<Otp> getOtpById(String username) {
        return otpRepository.findById(username);
    }

    // check if opt is expired
    public boolean isOtpExpired(String username) {
        Optional<Otp> otp = otpRepository.findById(username);

        long currentTimeSecs = System.currentTimeMillis() / 1000L;

        if (currentTimeSecs > otp.get().getExpiredTime()) {
            return true;
        } else {
            return false;
        }
    }

    // check if opt is correct
    public boolean isOtpCorrect(String username, String otpString) {
        Optional<Otp> otp = otpRepository.findById(username);

        if (otp.get().getOtp().equals(otpString)) {
            return true;
        } else {
            return false;
        }
    }

    // create otp if it's not in Otp table
    public void createOtp(String username) {
        String generatedOtp = otpGenerator();
        
        long currentTimeSecs = System.currentTimeMillis() / 1000L;

        long expiredTimeSecs = currentTimeSecs + EXPIRED_DURATION_IN_SECOND;

        Otp otp = new Otp(username, generatedOtp, currentTimeSecs, expiredTimeSecs);

        otpRepository.save(otp);
    }

    // refresh otp if it's int in Otp table
    @Transactional
    public void refreshOtp(String username) {
        String generatedOtp = otpGenerator();

        long currentTimeSecs = System.currentTimeMillis() / 1000L;

        long expiredTimeSecs = currentTimeSecs + EXPIRED_DURATION_IN_SECOND;

        otpRepository.findById(username).map(target -> {
            target.setOtp(generatedOtp);
            target.setCreatedTime(currentTimeSecs);
            target.setExpiredTime(expiredTimeSecs);
            return target; 
        });
    }

    // funtion to generate 4 - digit otp randomly
    public String otpGenerator() {
        String otp = "";

        for (int i = 0; i < NO_OTP_DIGIT; i++) {
            otp += myRandom(0, 9);
        }

        return otp;
    }

    // funtion to random an interger between min and max
    public int myRandom(int min, int max) {
        return (int)(Math.random() * (max - min + 0.99) + min);
    }

    // function to get otp html
    public String getOtpHtml() {
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
                        "            <h1 style=\"color: #4a90e2; margin-bottom: 20px;\">Prove Your Signer Identity</h1>\r\n" + //
                        "            <p style=\"color: #333; line-height: 1.6; margin-bottom: 20px;\">Hello <b>{username}</b>,</p>\r\n" + //
                        "            <p style=\"color: #333; line-height: 1.6; margin-bottom: 20px;\">You are required to enter the following code to confirm your account sent by Docsavior. Please enter the code in <strong>5 minutes</strong>.</p>\r\n" + //
                        "            <p style=\"color: #333; line-height: 1.6; margin-bottom: 20px;\">Your verification code:</p>\r\n" + //
                        "            <div style=\"font-size: 32px; font-weight: bold; color: #333; margin-bottom: 20px;\">{otp_code}</div>\r\n" + //
                        "        </main>\r\n" + //
                        "        <footer>\r\n" + //
                        "            <p style=\"font-size: 14px; color: #888; margin: 10px 0;\">If this wasn't you, please ignore this email or contact our customer service center: <a href=\"mailto:docsavior.service@gmail.com\" style=\"color: #4a90e2; text-decoration: underline; margin: 0 5px;\">docsavior.service@gmail.com</a> for further assistance.</p>\r\n" + //
                        "        </footer>\r\n" + //
                        "    </div>\r\n" + //
                        "</body>\r\n" + //
                        "</html>";
    }
}