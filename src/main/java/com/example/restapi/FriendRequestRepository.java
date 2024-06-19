package com.example.restapi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
    @Query("SELECT f.requester FROM FriendRequest f WHERE f.username = :username")
    List<String> findRequestersByUsername(@Param("username") String username);
}
