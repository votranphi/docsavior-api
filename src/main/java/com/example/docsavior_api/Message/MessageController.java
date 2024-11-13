package com.example.docsavior_api.Message;

import org.springframework.web.bind.annotation.RestController;

import com.example.docsavior_api.Others.Detail;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<List<Message>> getMyMessage(@RequestParam String username, @RequestParam String sender) {
        List<Message> messages = messageService.getMessageByUsername(username, sender);

        // sort the list
        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message message1, Message message2) {
                return (message1.getTime() < message2.getTime()) ? -1 : (message1.getTime() > message2.getTime()) ? 1 : 0;
            }
        });

        return ResponseEntity.ok(messages);
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestMessage(@RequestParam String username, @RequestParam String sender) {
        List<Message> messages = messageService.getMessageByUsername(username, sender);

        if (messages.isEmpty()) {
            return new ResponseEntity<>(new Detail("There is no message!"), HttpStatusCode.valueOf(600));
        }

        // sort the list
        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message message1, Message message2) {
                return (message1.getTime() < message2.getTime()) ? -1 : (message1.getTime() > message2.getTime()) ? 1 : 0;
            }
        });

        Message i = messages.get(messages.size() - 1);

        return ResponseEntity.ok(i);
    }

    @PostMapping("/add")
    public ResponseEntity<Detail> postMessage(@RequestParam String username, @RequestParam String sender, @RequestParam String content) {
        Message message = new Message(username, sender, content);

        messageService.saveMessage(message);
        
        return ResponseEntity.ok(new Detail("Post message successfully!"));
    }

    @GetMapping("/messaged_user")
    public ResponseEntity<?> getMessagedUsername(@RequestParam String username) {
        List<String> meUs = messageService.getMessagedUsername(username);

        if (meUs.isEmpty()) {
            return new ResponseEntity<>(new Detail("There is no conversation!"), HttpStatusCode.valueOf(600));
        }

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String i : meUs) {
            jsonArray.put(i);
        }
        jsonObject.put("messagedUsernames", jsonArray);

        return ResponseEntity.ok(jsonObject.toString());
    }

    @GetMapping("/unseen")
    public ResponseEntity<List<Message>> getUnseenMessage(@RequestParam String username, @RequestParam String sender) {
        List<Message> unseenMessages = messageService.getUnseenMessageByUsername(username, sender);

        return ResponseEntity.ok(unseenMessages);
    }
    
    @PostMapping("/seen_to_true")
    public ResponseEntity<Detail> postSeenToTrue(@RequestParam Integer id) {
        messageService.updateIsSeenToTrue(id);

        return ResponseEntity.ok(new Detail("Update isSeen to true successfully!"));
    }
    
}