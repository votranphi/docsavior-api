package com.example.restapi;

import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsfeedRepository extends JpaRepository<Newsfeed, Integer> {
    Page<Newsfeed> findByUsername(@Param("username") String username, Pageable pageable);
}