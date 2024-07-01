package com.example.restapi;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
            jsonObject.put("time", i.getTime());

            jsonArray.put(jsonObject);
        }

        return ResponseEntity.ok(jsonArray.toString());
    }

    @GetMapping("/post")
    public ResponseEntity<?> getSpecificPosts(@RequestParam Integer page, @RequestParam Integer pageSize) {
        Page<Newsfeed> posts = newsfeedService.getSequenceOfPost(page, pageSize);

        if (posts.isEmpty()) {
            return new ResponseEntity<>(new Detail("There's no post to show!"), HttpStatusCode.valueOf(600));
        }

        JSONArray jsonArray = new JSONArray();

        for (Newsfeed i : posts) {
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
            jsonObject.put("time", i.getTime());

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
            jsonObject.put("time", i.getTime());

            jsonArray.put(jsonObject);
        }

        return ResponseEntity.ok(jsonArray.toString());
    }
    
    @PostMapping("/look_up")
    public ResponseEntity<?> postLookUpPost(@RequestParam String lookUpInfo) {
        // get all users in database
        List<Newsfeed> newsfeeds = newsfeedService.getAllPosts();

        // lowercase the lookUpInfo and remove the leading/trailing space if it has
        String lowercasedLKI = lookUpInfo.toLowerCase().trim();

        // split the string to do the searching
        String[] spl = lowercasedLKI.split("\s");

        // init the foundUsers which stores found users
        List<Newsfeed> foundNewsfeeds = new ArrayList<>();

        // found newsfeed
        for (Newsfeed i : newsfeeds) {
            for (String j : spl) {
                // add the newfeed if it's contains at least one word of the lookUpInfo
                if (
                    i.getPostDescription().toLowerCase().contains(j) ||
                    i.getPostContent().toLowerCase().contains(j) ||
                    i.getFileName().toLowerCase().contains(j) ||
                    i.getFileExtension().toLowerCase().contains(j)
                ) {
                    foundNewsfeeds.add(i);
                    break;
                }
            }
        }

        // init a jsonObject
        JSONObject jsonObject = new JSONObject();

        // init a jsonArray
        JSONArray jsonArray = new JSONArray();

        // convert foundUsers to jsonArray
        for (Newsfeed i : foundNewsfeeds) {
            JSONObject temp = new JSONObject();
            temp.put("id", i.getId());
            temp.put("username", i.getUsername());
            temp.put("postDescription", i.getPostDescription());
            temp.put("postContent", i.getPostContent());
            temp.put("likeNumber", i.getLikeNumber());
            temp.put("dislikeNumber", i.getDislikeNumber());
            temp.put("commentNumber", i.getCommentNumber());
            temp.put("fileData", i.getFileData());
            temp.put("fileName", i.getFileName());
            temp.put("fileExtension", i.getFileExtension());
            temp.put("time", i.getTime());

            jsonArray.put(temp);
        }

        // put the jsonArray to jsonObject
        jsonObject.put("foundNewsfeeds", jsonArray);

        return ResponseEntity.ok(jsonObject.toString());
    }

    @GetMapping("/id")
    public ResponseEntity<?> getMethodName(@RequestParam Integer id) {
        Optional<Newsfeed> i = newsfeedService.getNewsfeedById(id);

        JSONObject temp = new JSONObject();
        temp.put("id", i.get().getId());
        temp.put("username", i.get().getUsername());
        temp.put("postDescription", i.get().getPostDescription());
        temp.put("postContent", i.get().getPostContent());
        temp.put("likeNumber", i.get().getLikeNumber());
        temp.put("dislikeNumber", i.get().getDislikeNumber());
        temp.put("commentNumber", i.get().getCommentNumber());
        temp.put("fileData", i.get().getFileData());
        temp.put("fileName", i.get().getFileName());
        temp.put("fileExtension", i.get().getFileExtension());
        temp.put("time", i.get().getTime());

        return ResponseEntity.ok(temp.toString());
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

    @PostMapping("/comment")
    public ResponseEntity<?> postComment(@RequestParam Integer id)
    {
        newsfeedService.updateCommentNumber(id);

        return ResponseEntity.ok(new Detail("Comment successfully!"));
    }
    
    @PostMapping("/uncomment")
    public ResponseEntity<?> postUncomment(@RequestParam Integer id)
    {
        newsfeedService.updateUnCommentNumber(id);

        return ResponseEntity.ok(new Detail("Uncomment successfully!"));
    }

    @GetMapping("/size")
    public int getNumberOfPosts() {
        return newsfeedService.NumberOfPosts();
    }
    
}   
