package com.example.restapi;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LookUpHistoryService {
    @Autowired
    LookUpHistoryRepository lookUpHistoryRepository;

    // method to save the new record to table
    public void saveNewLookUpHistory(LookUpHistory lookUpHistory) {
        lookUpHistoryRepository.save(lookUpHistory);
    }

    // method to get all the post's LookUpHistory of of a user by username
    public List<String> findPostLookUpHistoryByUsername(String username) {
        return lookUpHistoryRepository.findPostLookUpHistoryByUsername(username);
    }

    // method to get all the friend's LookUpHistory of a user by username
    public List<String> findUserLookUpHistoryByUsername(String username) {
        return lookUpHistoryRepository.findUserLookUpHistoryByUsername(username);
    }
}
