package com.example.restapi;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<Detail> postComment(@RequestParam String username, @RequestParam Integer idPost, @RequestParam String commentContent) {
        
        Comment comment = new Comment(username, idPost, commentContent);

        commentService.saveComment(comment);

        Integer addedIdComment = commentService.findHehe(username, idPost, commentContent);

        return ResponseEntity.ok(new Detail(String.valueOf(addedIdComment)));
    }
    

    @GetMapping("/comment_on_post")
    public ResponseEntity<List<Comment>> getAllCommentOnAPost(@RequestParam Integer idPost) {
        List<Comment> comments = commentService.findCommentByIdPost(idPost);

        return ResponseEntity.ok(comments);
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<Detail> deleteComment(@RequestParam Integer idComment) {
        commentService.deleteCommentById(idComment);

        return ResponseEntity.ok(new Detail("Comment deleted successfullt!"));
    }
}
