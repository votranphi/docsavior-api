package com.example.restapi;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NewsfeedRepository extends JpaRepository<Newsfeed, Integer> {
    @Query("SELECT n FROM Newsfeed n WHERE username = :username")
    List<Newsfeed> findNewsfeedByUsername(@Param("username") String username);
}