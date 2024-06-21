package com.example.restapi;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping(value = "/look_up_history")
public class LookUpHistoryController {
    @Autowired
    LookUpHistoryService lookUpHistoryService = new LookUpHistoryService();

    @PostMapping("/add")
    public ResponseEntity<?> postMethodName(@RequestParam String username, @RequestParam String lookUpInfo, @RequestParam Integer lookUpType) {
        LookUpHistory lookUpHistory = new LookUpHistory(username, lookUpInfo, lookUpType);

        lookUpHistoryService.saveNewLookUpHistory(lookUpHistory);
        
        return ResponseEntity.ok(new Detail("New lookup history saved successfully!"));
    }

    @GetMapping("/me/post")
    public ResponseEntity<?> getMyPostLookUpHistory(@RequestParam String username) {
        List<String> lookUpInfos = lookUpHistoryService.findPostLookUpHistoryByUsername(username);

        JSONObject jsonObject = new JSONObject();

        JSONArray jsonArray = new JSONArray();

        // put those lookup infos in jsonArray
        for (String i : lookUpInfos) {
            jsonArray.put(i);
        }

        jsonObject.put("lookUpInfos", jsonArray);

        return ResponseEntity.ok(jsonObject.toString());
    }
    
    @GetMapping("/me/friend")
    public ResponseEntity<?> getMyFriendLookUpHistory(@RequestParam String username) {
        List<String> lookUpInfos = lookUpHistoryService.findFriendLookUpHistoryByUsername(username);

        JSONObject jsonObject = new JSONObject();

        JSONArray jsonArray = new JSONArray();

        // put those lookup infos in jsonArray
        for (String i : lookUpInfos) {
            jsonArray.put(i);
        }

        jsonObject.put("lookUpInfos", jsonArray);

        return ResponseEntity.ok(jsonObject.toString());
    }
}
