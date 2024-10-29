package com.example.docsavior_api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Integer> {
    @Query("SELECT f.usernameFriend FROM Friend f WHERE f.username = :username UNION SELECT f.username FROM Friend f WHERE f.usernameFriend = :username")
    List<String> findFriendsByUsername(@Param("username") String username);

    @Modifying
    @Query("DELETE FROM Friend f WHERE (f.username = :username AND f.usernameFriend = :usernameFriend) OR (f.username = :usernameFriend AND f.usernameFriend = :username)")
    void deleteFriend(@Param("username") String username, @Param("usernameFriend") String usernameFriend);
}
