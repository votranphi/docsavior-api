package com.example.restapi;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping(value = "/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService = new NotificationService();

    @GetMapping("/all")
    public ResponseEntity<?> getAllNotifications(@RequestParam String username) {
        
        List<Notification> notifications = notificationService.findNotificationByUsername(username);

        JSONArray jsonArray = new JSONArray();
        for (Notification i : notifications)
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", i.getIdNotification());
            jsonObject.put("username", i.getUsername());
            jsonObject.put("type", i.getType());
            jsonObject.put("idPost", i.getIdPost());
            jsonObject.put("usernameInteract", i.getUserInteract());
            jsonArray.put(jsonObject);
        }
        return ResponseEntity.ok(jsonArray.toString());

    }
    
    @PostMapping("/add/type01")
    public ResponseEntity<?> postNewNotificationType01(@RequestParam String username, @RequestParam Integer type, @RequestParam Integer idPost, @RequestParam String usernameInteract) 
    {
        notificationService.saveNewNotificationType01(username, type, idPost, usernameInteract);
        return ResponseEntity.ok(new Detail("Notification saved successfully!"));
    }

    @PostMapping("/add/type2")
    public ResponseEntity<?> postNewNotification(@RequestParam String username, @RequestParam Integer type, @RequestParam String usernameInteract) 
    {
        notificationService.saveNewNotificationType2(username, type, usernameInteract);
        return ResponseEntity.ok(new Detail("Notification saved successfully!"));
    }
    
}
