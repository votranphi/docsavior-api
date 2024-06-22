package com.example.restapi;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LookUpHistoryRepository extends JpaRepository<LookUpHistory, Integer> {

    @Query("SELECT l.lookUpInfo FROM LookUpHistory l WHERE l.username = :username AND l.lookUpType = 0")
    List<String> findPostLookUpHistoryByUsername(@Param("username") String username);

    @Query("SELECT l.lookUpInfo FROM LookUpHistory l WHERE l.username = :username AND l.lookUpType = 2")
    List<String> findUserLookUpHistoryByUsername(@Param("username") String username);

    @Query("SELECT count(*) FROM LookUpHistory l WHERE l.username = :username AND l.lookUpInfo = :lookUpInfo AND l.lookUpType = :lookUpType")
    Integer findLookUpHistoryByUsernameAndLookUpInfoAndLookUpType(@Param("username") String username, @Param("lookUpInfo") String lookUpInfo, @Param("lookUpType") Integer lookUpType);

    @Modifying
    @Query("DELETE FROM LookUpHistory l WHERE l.username = :username AND l.lookUpInfo = :lookUpInfo AND l.lookUpType = :lookUpType")
    void deleteLookUpHistoryByUsernameAndLookUpInfoAndLookUpType(@Param("username") String username, @Param("lookUpInfo") String lookUpInfo, @Param("lookUpType") Integer lookUpType);
}
