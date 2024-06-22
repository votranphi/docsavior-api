package com.example.restapi;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping(value = "/comment")
public class CommentController {
    @Autowired
    private CommentService commentService = new CommentService();

    @PostMapping("/add")
    public ResponseEntity<?> postComment(@RequestParam String username, @RequestParam Integer idPost, @RequestParam String commentContent) {
        
        Comment comment = new Comment(username, idPost, commentContent);

        commentService.saveComment(comment);

        return ResponseEntity.ok(new Detail("Save comment successfully!"));
    }
    

    @GetMapping("/comment_on_post")
    public ResponseEntity<?> getAllCommentOnAPost(@RequestParam Integer idPost) {
        List<Comment> comments = commentService.findCommentByIdPost(idPost);

        JSONArray jsonArray = new JSONArray();

        for (Comment i : comments) {
            JSONObject temp = new JSONObject();

            temp.put("idComment", i.getIdComment());
            temp.put("username", i.getUsername());
            temp.put("idPost", i.getIdPost());
            temp.put("commentContent", i.getCommentContent());
            temp.put("time", i.getTime());

            jsonArray.put(temp);
        }

        return ResponseEntity.ok(jsonArray.toString());
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestParam Integer idComment) {
        commentService.deleteCommentById(idComment);

        return ResponseEntity.ok(new Detail("Comment deleted successfullt!"));
    }
}
