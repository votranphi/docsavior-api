package com.example.restapi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT m FROM Message m WHERE m.username = :username AND m.sender = :sender UNION SELECT m FROM Message m WHERE m.username = :sender AND m.sender = :username")
    List<Message> findMessageByUsername(@Param("username") String username, @Param("sender") String sender);
}