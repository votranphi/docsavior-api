package com.example.docsavior_api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT m FROM Message m WHERE m.username = :username AND m.sender = :sender ORDER BY m.time ASC UNION SELECT m FROM Message m WHERE m.username = :sender AND m.sender = :username ORDER BY m.time ASC")
    List<Message> findMessageByUsername(@Param("username") String username, @Param("sender") String sender);

    @Query("SELECT m.username FROM Message m WHERE m.sender = :username UNION SELECT m.sender FROM Message m WHERE m.username = :username")
    List<String> findMessagedUsername(@Param("username") String username);

    @Query("SELECT m FROM Message m WHERE m.username = :username AND m.sender = :sender AND m.isSeen = false ORDER BY m.time ASC")
    List<Message> findUnseenMessageByUsername(@Param("username") String username, @Param("sender") String sender);
}