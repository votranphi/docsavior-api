package com.example.restapi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping(value = "/friend")
public class FriendController {
    @Autowired
    private FriendService friendService = new FriendService();

    @GetMapping("/all")
    public ResponseEntity<?> getAllFriends(@RequestParam String username) {
        List<String> friends = friendService.findFriendsByUsername(username);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String i : friends) {
            jsonArray.put(i);
        }
        jsonObject.put("friends", jsonArray);

        return ResponseEntity.ok(jsonObject.toString());
    }
    
    @PostMapping("/add")
    public ResponseEntity<?> postNewFriend(@RequestParam String username, @RequestParam String usernameFriend) {
        friendService.saveNewFriend(username, usernameFriend);

        return ResponseEntity.ok(new Detail("Friend saved successfully!"));
    }
}
