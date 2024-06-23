package com.example.restapi;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<?> getMyNotification(@RequestParam String username) {
        List<Notification> notifications = notificationService.findNotificationByUsername(username);

        JSONArray jsonArray = new JSONArray();

        for (Notification i : notifications) {
            JSONObject temp = new JSONObject();
            temp.put("idNotification", i.getIdNotification());
            temp.put("username", i.getUsername());
            temp.put("type", i.getType());
            temp.put("idPost", i.getIdPost());
            temp.put("interacter", i.getInteracter());
            temp.put("notificationContent", i.getNotificationContent());
            temp.put("time", i.getTime());

            jsonArray.put(temp);
        }

        return ResponseEntity.ok(jsonArray.toString());
    }
    

    @PostMapping("/add")
    public ResponseEntity<?> postNotification(@RequestParam String username, @RequestParam Integer type,  @RequestParam Integer idPost, @RequestParam String interacter) {
        Notification notification = new Notification(username, type, idPost, interacter);

        notificationService.saveNewNotification(notification);
        
        return ResponseEntity.ok(new Detail("Notification added successfully!"));
    }
    

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteNotification(@RequestParam String username, @RequestParam Integer type,  @RequestParam Integer idPost, @RequestParam String interacter) {
        notificationService.deleteNotificationById(username, type, idPost, interacter);

        return ResponseEntity.ok(new Detail("Notification deleted successfully!"));
    }
    
}
