package com.example.docsavior_api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT c FROM Comment c WHERE idPost = :idPost ORDER BY c.time DESC")
    List<Comment> findCommentByIdPost(@Param("idPost") Integer idPost);

    @Query("SELECT c.idComment FROM Comment c WHERE c.username = :username AND c.idPost = :idPost AND c.commentContent = :commentContent")
    Integer findHehe(@Param("username") String username, @Param("idPost") Integer idPost, @Param("commentContent") String commentContent);
}
