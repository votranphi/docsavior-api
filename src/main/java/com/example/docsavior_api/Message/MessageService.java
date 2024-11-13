package com.example.docsavior_api.Message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

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

    // function to get the username of the messaged user
    public List<String> getMessagedUsername(String username) {
        return messageRepository.findMessagedUsername(username);
    }

    // function to get unseen message from username
    public List<Message> getUnseenMessageByUsername(String username, String sender) {
        return messageRepository.findUnseenMessageByUsername(username, sender);
    }

    // function to do updating isSeen = true to the Message table
    @Transactional
    public void updateIsSeenToTrue(Integer id) {
        messageRepository.findById(id).map(target -> {
            target.setIsSeen(true);
            return target; 
        });
    }
}
