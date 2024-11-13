package com.example.docsavior_api.Newsfeed;

import org.springframework.data.repository.query.Param;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NewsfeedRepository extends JpaRepository<Newsfeed, Integer> {
    Page<Newsfeed> findByUsername(@Param("username") String username, Pageable pageable);
    
    @Query("SELECT n FROM Newsfeed n WHERE n.username = :username")
    List<Newsfeed> findByUsername(@Param("username") String username);
}