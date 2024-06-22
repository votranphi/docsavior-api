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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping(value = "/look_up_history")
public class LookUpHistoryController {
    @Autowired
    LookUpHistoryService lookUpHistoryService = new LookUpHistoryService();

    @PostMapping("/add")
    public ResponseEntity<?> postLookUpHistory(@RequestParam String username, @RequestParam String lookUpInfo, @RequestParam Integer lookUpType) {
        LookUpHistory lookUpHistory = new LookUpHistory(username, lookUpInfo, lookUpType);

        // add look up history to database if it wasn't added
        if (!lookUpHistoryService.isLookUpHistoryAdded(username, lookUpInfo, lookUpType)) {
            lookUpHistoryService.saveNewLookUpHistory(lookUpHistory);
        }
        
        return ResponseEntity.ok(new Detail("New lookup history saved successfully!"));
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteLookUpHistory(@RequestParam String username, @RequestParam String lookUpInfo, @RequestParam Integer lookUpType) {
        lookUpHistoryService.deleteLookUpHistory(username, lookUpInfo, lookUpType);

        return ResponseEntity.ok(new Detail("New lookup history deleted successfully!"));
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
    public ResponseEntity<?> getMyUserLookUpHistory(@RequestParam String username) {
        List<String> lookUpInfos = lookUpHistoryService.findUserLookUpHistoryByUsername(username);

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
