package com.example.docsavior_api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
    @Query("SELECT f.requester FROM FriendRequest f WHERE f.username = :username")
    List<String> findRequestersByUsername(@Param("username") String username);

    @Modifying
    @Query("DELETE FROM FriendRequest f WHERE f.username = :username AND f.requester = :requester")
    void deleteFriendRequestByUsernameAndRequester(@Param("username") String username, @Param("requester") String requester);
}
