package com.example.restapi;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LookUpHistoryRepository extends JpaRepository<LookUpHistory, Integer> {

    @Query("SELECT l.lookUpInfo FROM LookUpHistory l WHERE l.username = :username AND l.lookUpType = 0")
    List<String> findPostLookUpHistoryByUsername(@Param("username") String username);

    @Query("SELECT l.lookUpInfo FROM LookUpHistory l WHERE l.username = :username AND l.lookUpType = 2")
    List<String> findFriendLookUpHistoryByUsername(@Param("username") String username);

}
