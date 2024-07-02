package com.example.restapi;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/friend_request")
public class FriendRequestController {
    @Autowired
    private FriendRequestService friendRequestService = new FriendRequestService();

    @GetMapping("/all")
    public ResponseEntity<?> getAllFriendRequests(@RequestParam String username) {
        List<String> friends = friendRequestService.findFriendsByUsername(username);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String i : friends) {
            jsonArray.put(i);
        }
        jsonObject.put("requesters", jsonArray);

        return ResponseEntity.ok(jsonObject.toString());
    }
    
    @PostMapping("/add")
    public ResponseEntity<Detail> postNewFriendRequest(@RequestParam String username, @RequestParam String requester) {
        friendRequestService.saveNewFriendRequest(username, requester);

        return ResponseEntity.ok(new Detail("Friend request saved successfully!"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Detail> deleteFriendRequest(@RequestParam String username, @RequestParam String requester) {
        friendRequestService.deleteFriendRequest(username, requester);

        return ResponseEntity.ok(new Detail("Friend request deleted successfully!"));
    }
}