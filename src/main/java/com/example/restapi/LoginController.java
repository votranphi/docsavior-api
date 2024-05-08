package com.example.restapi;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping(value = "/api")
public class LoginController {
    
    @PostMapping("/login")
    public ResponseEntity<?> postLogin(@RequestParam String username, @RequestParam String password) {
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

            if (username.equals((String)temp.get("username"))) {
                if (password.equals((String)temp.getString("password"))) {
                    return ResponseEntity.ok(new Detail("Login successfully!"));
                } else {
                    return new ResponseEntity<>(new Detail("Wrong password!"), HttpStatusCode.valueOf(401));
                }
            }
        }

        return new ResponseEntity<>(new Detail("Username doesn't exist!"), HttpStatusCode.valueOf(401));
    }
    
}