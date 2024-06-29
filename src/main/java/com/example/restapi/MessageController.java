package com.example.restapi;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;




@RestController
@RequestMapping(value = "/message")
public class MessageController {
    @Autowired
    private MessageService messageService = new MessageService();

    @GetMapping("/all")
    public ResponseEntity<?> getMyMessage(@RequestParam String username, @RequestParam String sender) {
        List<Message> messages = messageService.getMessageByUsername(username, sender);

        JSONArray jsonArray = new JSONArray();

        for (Message i : messages) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", i.getId());
            jsonObject.put("username", i.getUsername());
            jsonObject.put("sender", i.getSender());
            jsonObject.put("content", i.getContent());
            jsonObject.put("isSeen", i.getIsSeen());
            jsonObject.put("time", i.getTime());

            jsonArray.put(jsonObject);
        }

        return ResponseEntity.ok(jsonArray.toString());
    }

    @PostMapping("/add")
    public ResponseEntity<?> postMessage(@RequestParam String username, @RequestParam String sender, @RequestParam String content) {
        Message message = new Message(username, sender, content);

        messageService.saveMessage(message);
        
        return ResponseEntity.ok(new Detail("Post message successfully!"));
    }
}