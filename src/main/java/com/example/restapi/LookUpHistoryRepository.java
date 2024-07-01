package com.example.restapi;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LookUpHistoryRepository extends JpaRepository<LookUpHistory, Integer> {

    @Query("SELECT l.lookUpInfo FROM LookUpHistory l WHERE l.username = :username AND l.lookUpType = 0 ORDER BY l.time DESC")
    List<String> findPostLookUpHistoryByUsername(@Param("username") String username);
    
    @Query("SELECT l.lookUpInfo FROM LookUpHistory l WHERE l.username = :username AND l.lookUpType = 1 ORDER BY l.time DESC")
    List<String> findChatLookUpHistoryByUsername(@Param("username") String username);

    @Query("SELECT l.lookUpInfo FROM LookUpHistory l WHERE l.username = :username AND l.lookUpType = 2 ORDER BY l.time DESC")
    List<String> findUserLookUpHistoryByUsername(@Param("username") String username);

    @Query("SELECT count(*) FROM LookUpHistory l WHERE l.username = :username AND l.lookUpInfo = :lookUpInfo AND l.lookUpType = :lookUpType")
    Integer findNumberOfLookUpHistoryByUsernameAndLookUpInfoAndLookUpType(@Param("username") String username, @Param("lookUpInfo") String lookUpInfo, @Param("lookUpType") Integer lookUpType);

    @Modifying
    @Query("DELETE FROM LookUpHistory l WHERE l.username = :username AND l.lookUpInfo = :lookUpInfo AND l.lookUpType = :lookUpType")
    void deleteLookUpHistoryByUsernameAndLookUpInfoAndLookUpType(@Param("username") String username, @Param("lookUpInfo") String lookUpInfo, @Param("lookUpType") Integer lookUpType);

    @Modifying
    @Query("UPDATE LookUpHistory l SET l.time = :time WHERE l.username = :username AND l.lookUpInfo = :lookUpInfo AND l.lookUpType = :lookUpType")
    void updateTimeByUsernameAndLookUpInfoAndLookUpType(@Param("username") String username, @Param("lookUpInfo") String lookUpInfo, @Param("lookUpType") Integer lookUpType, @Param("time") Long time);
}
