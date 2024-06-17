package com.example.restapi;

import org.springframework.web.bind.annotation.RestController;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            jsonObject.put("id", i.getId());
            jsonObject.put("username", i.getUsername());
            jsonObject.put("postDescription", i.getPostDescription());
            jsonObject.put("postContent", i.getPostContent());
            jsonObject.put("likeNumber", i.getLikeNumber());
            jsonObject.put("dislikeNumber", i.getDislikeNumber());
            jsonObject.put("commentNumber", i.getCommentNumber());
            jsonObject.put("newsfeedFileData", i.getNewsfeedFileData());
            jsonObject.put("newsfeedFileExtension", i.getNewsfeedFileExtension());

            jsonArray.put(jsonObject);
        }

        return ResponseEntity.ok(jsonArray.toString());
    }

    @PostMapping("/add")
    public ResponseEntity<?> postPost(@RequestParam String username, @RequestParam String postDescription, @RequestParam String postContent, @RequestParam String newsfeedFileData, @RequestParam String newsfeedFileExtension) {
        
        // create new Newsfeed entity
        Newsfeed newNewsfeed = new Newsfeed(username, postDescription, postContent, newsfeedFileData, newsfeedFileExtension);

        // save newNewsfeed
        newfeedService.saveNewNewsfeed(newNewsfeed);
        
        return ResponseEntity.ok(new Detail("Post successfully!"));
    }
    
}   
