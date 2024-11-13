package com.example.docsavior_api.Notification;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    
    // function to get all notification by username
    public List<Notification> findNotificationByUsername(String username) {
        return notificationRepository.findNotificationByUsername(username);
    }

    // function to save a new notification
    public void saveNewNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    // function to delete notification by id
    @Transactional
    public void deleteNotificationById(String username, Integer type, Integer idPost, String interacter) {
        notificationRepository.deleteNotificationById(username, type, idPost, interacter);
    }
}
