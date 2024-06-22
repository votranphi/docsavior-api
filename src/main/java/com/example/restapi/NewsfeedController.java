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
    NewsfeedService newsfeedService = new NewsfeedService();

    @GetMapping("/all")
    public ResponseEntity<?> getAllPosts() {
        var allPosts = newsfeedService.getAllPosts();

        if (allPosts.size() == 0) {
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
            jsonObject.put("fileData", i.getFileData());
            jsonObject.put("fileName", i.getFileName());
            jsonObject.put("fileExtension", i.getFileExtension());

            jsonArray.put(jsonObject);
        }

        return ResponseEntity.ok(jsonArray.toString());
    }

    @PostMapping("/add")
    public ResponseEntity<?> postPost(@RequestParam String username, @RequestParam String postDescription, @RequestParam String postContent, @RequestParam String fileData, @RequestParam String fileName, @RequestParam String fileExtension) {
        
        // create new Newsfeed entity
        Newsfeed newNewsfeed = new Newsfeed(username, postDescription, postContent, fileData, fileName, fileExtension);

        // save newNewsfeed
        newsfeedService.saveNewNewsfeed(newNewsfeed);
        
        return ResponseEntity.ok(new Detail("Post successfully!"));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyPost(@RequestParam String username) {
        var allMyPosts = newsfeedService.getMyPost(username);

        if (allMyPosts.size() == 0) {
            return new ResponseEntity<>(new Detail("There's no post to show!"), HttpStatusCode.valueOf(600));
        }

        JSONArray jsonArray = new JSONArray();

        for (Newsfeed i : allMyPosts) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", i.getId());
            jsonObject.put("username", i.getUsername());
            jsonObject.put("postDescription", i.getPostDescription());
            jsonObject.put("postContent", i.getPostContent());
            jsonObject.put("likeNumber", i.getLikeNumber());
            jsonObject.put("dislikeNumber", i.getDislikeNumber());
            jsonObject.put("commentNumber", i.getCommentNumber());
            jsonObject.put("fileData", i.getFileData());
            jsonObject.put("fileName", i.getFileName());
            jsonObject.put("fileExtension", i.getFileExtension());

            jsonArray.put(jsonObject);
        }

        return ResponseEntity.ok(jsonArray.toString());
    }
    

    @PostMapping("/like")
    public ResponseEntity<?> postLike(@RequestParam Integer id)
    {
        newsfeedService.updateLikeNumber(id);

        return ResponseEntity.ok(new Detail("Like successfully!"));
    }

    @PostMapping("/unlike")
    public ResponseEntity<?> postUnlike(@RequestParam Integer id)
    {
        newsfeedService.updateUnlikeNumber(id);

        return ResponseEntity.ok(new Detail("Unike successfully!"));
    }

    @PostMapping("/dislike")
    public ResponseEntity<?> postDislike(@RequestParam Integer id)
    {
        newsfeedService.updateDislikeNumber(id);

        return ResponseEntity.ok(new Detail("Dislike successfully!"));
    }

    @PostMapping("/undislike")
    public ResponseEntity<?> postUndislike(@RequestParam Integer id)
    {
        newsfeedService.updateUndislikeNumber(id);

        return ResponseEntity.ok(new Detail("UnDislike successfully!"));
    }
    
}   
