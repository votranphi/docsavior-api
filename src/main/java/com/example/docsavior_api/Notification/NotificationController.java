package com.example.docsavior_api.Notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.docsavior_api.Others.Detail;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;





@RestController
@RequestMapping(value = "/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService = new NotificationService();

    @GetMapping("/me")
    public ResponseEntity<List<Notification>> getMyNotification(@RequestParam String username) {
        List<Notification> notifications = notificationService.findNotificationByUsername(username);

        return ResponseEntity.ok(notifications);
    }
    

    @PostMapping("/add")
    public ResponseEntity<Detail> postNotification(@RequestParam String username, @RequestParam Integer type,  @RequestParam Integer idPost, @RequestParam String interacter) {
        Notification notification = new Notification(username, type, idPost, interacter);

        notificationService.saveNewNotification(notification);
        
        return ResponseEntity.ok(new Detail("Notification added successfully!"));
    }
    

    @DeleteMapping("/delete")
    public ResponseEntity<Detail> deleteNotification(@RequestParam String username, @RequestParam Integer type,  @RequestParam Integer idPost, @RequestParam String interacter) {
        notificationService.deleteNotificationById(username, type, idPost, interacter);

        return ResponseEntity.ok(new Detail("Notification deleted successfully!"));
    }
    
}
