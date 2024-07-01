package com.example.restapi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



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

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFriend(@RequestParam String username, @RequestParam String usernameFriend) {
        friendService.deleteFriend(username, usernameFriend);

        return ResponseEntity.ok(new Detail("Friend deleted successfully!"));
    }

    @PostMapping("/look_up")
    public ResponseEntity<?> postLookUpFriend(@RequestParam String username, @RequestParam String lookUpInfo) {
        List<String> usernames = friendService.findFriendsByUsername(username);

        // lowercase the lookUpInfo and remove the leading/trailing space if it has
        String lowercasedLKI = lookUpInfo.toLowerCase().trim();

        // split the string to do the searching
        String[] spl = lowercasedLKI.split("\s");

        // init the foundUsers which stores found users
        List<String> founds = new ArrayList<>();

        // found user
        for (String i : usernames) {
            for (String j : spl) {
                // add the user if it's contains at least one word of the lookUpInfo
                if (i.contains(j)) {
                    founds.add(i);
                    break;
                }
            }
        }

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String i : founds) {
            jsonArray.put(i);
        }
        jsonObject.put("foundFriends", jsonArray);

        return ResponseEntity.ok(jsonObject.toString());
    }
    
}
