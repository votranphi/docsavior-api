package com.example.restapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping(value = "/api")
public class SignUpController {

    @PostMapping("/signup")
    public ResponseEntity<?> postSignUp(@RequestParam String username, @RequestParam String email, @RequestParam String phoneNumber, @RequestParam String password) {
        // check if the email is valid
        Pattern pattern = Pattern.compile(".+@gmail.com$");
        Matcher matcher = pattern.matcher(email);
        boolean isFound = matcher.find();
        if (!isFound) {
            return new ResponseEntity<>(new Detail("Email is invalid!"), HttpStatusCode.valueOf(401));
        }

        // check if the phone number is valid
        if (phoneNumber.length() != 10) {
            return new ResponseEntity<>(new Detail("Phone number is invalid!"), HttpStatusCode.valueOf(401));
        }

        // read all the text in UsersAccount.json to jsonString
        Path path = Paths.get("src\\main\\resources\\UserAccounts.json");
        String jsonString = "";
        try {
            jsonString = Files.readString(path);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        // check if the jsonString is empty
        JSONArray jsonArray = null;
        if (jsonString.equals("")) {
            // convert jsonString to jsonArray
            jsonArray = new JSONArray("[]");
        } else {
            // convert jsonString to jsonArray
            jsonArray = new JSONArray(jsonString);
        }

        // check if the username, email or phone number already exists
        for (Object object : jsonArray) {
            JSONObject temp = (JSONObject)object;

            if (username.equals((String)temp.get("username"))) {
                return new ResponseEntity<>(new Detail("Username already exists!"), HttpStatusCode.valueOf(401));
            }

            if (email.equals((String)temp.get("email"))) {
                return new ResponseEntity<>(new Detail("Email already exists!"), HttpStatusCode.valueOf(401));
            }

            if (phoneNumber.equals((String)temp.get("phoneNumber"))) {
                return new ResponseEntity<>(new Detail("Phone number already exists!"), HttpStatusCode.valueOf(401));
            }
        }

        // create new user
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("email", email);
        jsonObject.put("phoneNumber", phoneNumber);
        jsonObject.put("password", password);

        // add new user to jsonArray
        jsonArray.put(jsonObject);

        // convert jsonObject to jsonString
        jsonString = jsonArray.toString();

        // write the jsonString to UserAccounts.json
        try {
            PrintWriter printWriter = new PrintWriter("src\\main\\resources\\UserAccounts.json");
            printWriter.println(jsonString);
            printWriter.close();
        } catch (IOException ex) {
            System.out.println("File not found!");
        }

        return ResponseEntity.ok(new Detail("Sign up successfully!"));
    }
    
}