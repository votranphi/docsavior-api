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
}