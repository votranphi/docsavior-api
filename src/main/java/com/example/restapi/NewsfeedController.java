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
@RequestMapping(value = "/newsfeed")
public class NewsfeedController {
    @Autowired
    NewsfeedService newfeedService = new NewsfeedService();

    @GetMapping("/all")
    public ResponseEntity<?> getAllPosts() {
        var allPosts = newfeedService.getAllPosts();

        if (newfeedService.isEmpty()) {
            return new ResponseEntity<>(new Detail("There's no post to show!"), HttpStatusCode.valueOf(600));
        }

        JSONArray jsonArray = new JSONArray();

        for (Newsfeed i : allPosts) {
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
        Newsfeed newfeed1 = new Newsfeed("votranphi", "Cách chơi bài luôn thắng", "ABCDEFG", 100, 200, 300);
        Newsfeed newfeed2 = new Newsfeed("votranphi1", "Toán học 12", "ABCDEFG", 1, 2, 3);
        Newsfeed newfeed3 = new Newsfeed("votranphi2", "Ngữ văn 9", "ABCDEFG", 0, 0, 0);
        Newsfeed newfeed4 = new Newsfeed("votranphi3", "Đắc nhân tâm", "ABCDEFG", 6, 5, 4);
        Newsfeed newfeed5 = new Newsfeed("votranphi4", "Hạt giống tâm hồn", "ABCDEFG", 4, 5, 5);

        newfeedService.saveNewPost(newfeed1);
        newfeedService.saveNewPost(newfeed2);
        newfeedService.saveNewPost(newfeed3);
        newfeedService.saveNewPost(newfeed4);
        newfeedService.saveNewPost(newfeed5);
        
        return "";
    }
    
}   
