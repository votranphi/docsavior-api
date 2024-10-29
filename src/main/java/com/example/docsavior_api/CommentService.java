package com.example.docsavior_api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    // function to save new Comment
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    // function to find Comment of a post
    public List<Comment> findCommentByIdPost(Integer idPost) {
        return commentRepository.findCommentByIdPost(idPost);
    }

    // delete comment by id
    public void deleteCommentById(Integer idComment) {
        commentRepository.deleteById(idComment);
    }

    // function to get the hehe
    public Integer findHehe(String username, Integer idPost, String commentContent) {
        return commentRepository.findHehe(username, idPost, commentContent);
    }
}
