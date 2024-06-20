package com.example.restapi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer>{
    @Query("SELECT f FROM Notification f WHERE f.username = :username")
    List<Notification> findNotificationByUsername(@Param("username") String username);

}
