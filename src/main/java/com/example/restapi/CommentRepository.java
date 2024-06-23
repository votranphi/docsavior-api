package com.example.restapi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT c FROM Comment c WHERE idPost = :idPost ORDER BY c.time DESC")
    List<Comment> findCommentByIdPost(@Param("idPost") Integer idPost);
}
