package com.example.docsavior_api.Newsfeed;

import org.springframework.web.bind.annotation.RestController;

import com.example.docsavior_api.Others.Detail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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

        return ResponseEntity.ok(allPosts);
    }

    @GetMapping("/post")
    public ResponseEntity<?> getSpecificPosts(@RequestParam Integer page, @RequestParam Integer pageSize) {
        Page<Newsfeed> posts = newsfeedService.getSequenceOfPost(page, pageSize);

        if (posts.isEmpty()) {
            return new ResponseEntity<>(new Detail("There's no post to show!"), HttpStatusCode.valueOf(600));
        }

        return ResponseEntity.ok(posts.toList());
    }
    

    @PostMapping("/add")
    public ResponseEntity<Detail> postPost(@RequestBody Newsfeed newsfeed) {
        
        // create new Newsfeed entity
        Newsfeed newNewsfeed = new Newsfeed(newsfeed.getUsername(), newsfeed.getPostDescription(), newsfeed.getPostContent(), newsfeed.getFileData(), newsfeed.getFileName(), newsfeed.getFileExtension());

        // save newNewsfeed
        Integer id = newsfeedService.saveNewNewsfeed(newNewsfeed);
        
        // return the post id to client side
        return ResponseEntity.ok(new Detail(String.valueOf(id)));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyPost(@RequestParam String username, @RequestParam Integer page, @RequestParam Integer pageSize) {
        var allMyPosts = newsfeedService.getMyPost(username, page, pageSize);

        if (allMyPosts.toList().size() == 0) {
            return new ResponseEntity<>(new Detail("There's no post to show!"), HttpStatusCode.valueOf(600));
        }

        return ResponseEntity.ok(allMyPosts.toList());
    }
    
    @PostMapping("/look_up")
    public ResponseEntity<List<Newsfeed>> postLookUpPost(@RequestParam String lookUpInfo, @RequestParam Integer page, @RequestParam Integer pageSize) {
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

        Collections.sort(foundNewsfeeds, new Comparator<Newsfeed>() {
            @Override
            public int compare(Newsfeed newsfeed1, Newsfeed newsfeed2) {
                return (newsfeed1.getLikeMinusDislike() > newsfeed2.getLikeMinusDislike()) ? -1 : (newsfeed1.getLikeMinusDislike() < newsfeed2.getLikeMinusDislike()) ? 1 : 0;
            }
        });

        // get the specific sequence of post base on pagination
        List<Newsfeed> returnedNewsfeed = new ArrayList<>();
        for (int i = page * pageSize; i < (page + 1) * pageSize; i++) {
            if (i < foundNewsfeeds.size()) {
                returnedNewsfeed.add(foundNewsfeeds.get(i));
            }
        }

        return ResponseEntity.ok(returnedNewsfeed);
    }

    @GetMapping("/id")
    public ResponseEntity<Newsfeed> getMethodName(@RequestParam Integer id) {
        Optional<Newsfeed> i = newsfeedService.getNewsfeedById(id);

        return ResponseEntity.ok(i.get());
    }
    

    @PostMapping("/like")
    public ResponseEntity<Detail> postLike(@RequestParam Integer id)
    {
        newsfeedService.updateLikeNumber(id);

        return ResponseEntity.ok(new Detail("Like successfully!"));
    }

    @PostMapping("/unlike")
    public ResponseEntity<Detail> postUnlike(@RequestParam Integer id)
    {
        newsfeedService.updateUnlikeNumber(id);

        return ResponseEntity.ok(new Detail("Unike successfully!"));
    }

    @PostMapping("/dislike")
    public ResponseEntity<Detail> postDislike(@RequestParam Integer id)
    {
        newsfeedService.updateDislikeNumber(id);

        return ResponseEntity.ok(new Detail("Dislike successfully!"));
    }

    @PostMapping("/undislike")
    public ResponseEntity<Detail> postUndislike(@RequestParam Integer id)
    {
        newsfeedService.updateUndislikeNumber(id);

        return ResponseEntity.ok(new Detail("UnDislike successfully!"));
    }

    @PostMapping("/comment")
    public ResponseEntity<Detail> postComment(@RequestParam Integer id)
    {
        newsfeedService.updateCommentNumber(id);

        return ResponseEntity.ok(new Detail("Comment successfully!"));
    }
    
    @PostMapping("/uncomment")
    public ResponseEntity<Detail> postUncomment(@RequestParam Integer id)
    {
        newsfeedService.updateUnCommentNumber(id);

        return ResponseEntity.ok(new Detail("Uncomment successfully!"));
    }

    @GetMapping("/size")
    public int getNumberOfPosts() {
        return newsfeedService.NumberOfPosts();
    }
    
    @GetMapping("/me_size")
    public int getNUmberOfMyPost(@RequestParam String username) {
        var allMyPosts = newsfeedService.getAllMyPost(username);

        return allMyPosts.size();
    }

    @GetMapping("/found_size")
    public int getNumberOfFoundPost(@RequestParam String lookUpInfo) {
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

        return foundNewsfeeds.size();
    }    
}   
