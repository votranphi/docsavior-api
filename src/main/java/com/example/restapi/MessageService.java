package com.example.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    // function to get message from username
    public List<Message> getMessageByUsername(String username, String sender) {
        return messageRepository.findMessageByUsername(username, sender);
    }

    // function to save new message to datebase
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }
}
