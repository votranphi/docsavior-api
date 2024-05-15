package com.example.restapi;

import org.springframework.web.bind.annotation.RestController;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping(value = "/newfeed")
public class NewfeedController {
    @Autowired
    NewfeedService newfeedService = new NewfeedService();

    @GetMapping("/all")
    public ResponseEntity<?> getAllPosts() {
        var allPosts = newfeedService.getAllPosts();

        if (newfeedService.isEmpty()) {
            return new ResponseEntity<>(new Detail("There's no post to show!"), HttpStatusCode.valueOf(600));
        }

        JSONArray jsonArray = new JSONArray();

        for (Newfeed i : allPosts) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", i.getUsername());
            jsonObject.put("postDescription", i.getPostDescription());
            jsonObject.put("postContent", i.getPostContent());
            jsonObject.put("likeNumber", i.getLikeNumber());
            jsonObject.put("dislikeNumber", i.getDislikeNumber());
            jsonObject.put("commentNumber", i.getCommentNumber());

            jsonArray.put(jsonObject);
        }

        return ResponseEntity.ok(jsonArray.toString());
    }

    @PostMapping("/add")
    public String postPost() {
        // create fake dataset
        Newfeed newfeed1 = new Newfeed("votranphi", "Cách chơi bài luôn thắng", "ABCDEFG", 100, 200, 300);
        Newfeed newfeed2 = new Newfeed("votranphi1", "Toán học 12", "ABCDEFG", 1, 2, 3);
        Newfeed newfeed3 = new Newfeed("votranphi2", "Ngữ văn 9", "ABCDEFG", 0, 0, 0);
        Newfeed newfeed4 = new Newfeed("votranphi3", "Đắc nhân tâm", "ABCDEFG", 6, 5, 4);
        Newfeed newfeed5 = new Newfeed("votranphi4", "Hạt giống tâm hồn", "ABCDEFG", 4, 5, 5);

        newfeedService.saveNewPost(newfeed1);
        newfeedService.saveNewPost(newfeed2);
        newfeedService.saveNewPost(newfeed3);
        newfeedService.saveNewPost(newfeed4);
        newfeedService.saveNewPost(newfeed5);
        
        return "";
    }
    
}   
