package com.example.docsavior_api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer>{
    @Query("SELECT f FROM Notification f WHERE f.username = :username ORDER BY f.time DESC")
    List<Notification> findNotificationByUsername(@Param("username") String username);

    @Modifying
    @Query("DELETE FROM Notification n WHERE n.username = :username AND n.type = :type AND n.idPost = :idPost AND n.interacter = :interacter")
    void deleteNotificationById(@Param("username") String username, @Param("type") Integer type, @Param("idPost") Integer idPost, @Param("interacter") String interacter);
}
