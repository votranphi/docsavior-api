package com.example.restapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Integer> {
    @Query("SELECT f.usernameFriend FROM Friend f WHERE f.username = :username UNION SELECT f.username FROM Friend f WHERE f.usernameFriend = :username")
    List<String> findFriendsByUsername(@Param("username") String username);
}
