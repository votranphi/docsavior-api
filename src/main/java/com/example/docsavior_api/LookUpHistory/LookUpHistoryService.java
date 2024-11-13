package com.example.docsavior_api.LookUpHistory;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class LookUpHistoryService {
    @Autowired
    LookUpHistoryRepository lookUpHistoryRepository;

    // method to save the new record to table
    public void saveNewLookUpHistory(LookUpHistory lookUpHistory) {
        lookUpHistoryRepository.save(lookUpHistory);
    }

    // function to do updating look_up_history's time
    @Transactional
    public void updateLookupHistoryTime(LookUpHistory lookUpHistory) {
        lookUpHistoryRepository.updateTimeByUsernameAndLookUpInfoAndLookUpType(lookUpHistory.getUsername(), lookUpHistory.getLookUpInfo(), lookUpHistory.getLookUpType(), System.currentTimeMillis() / 1000L);
    }

    // method to get all the post's LookUpHistory of of a user by username
    public List<String> findPostLookUpHistoryByUsername(String username) {
        return lookUpHistoryRepository.findPostLookUpHistoryByUsername(username);
    }

    // method to get all the chat's LookUpHistory of a user by username
    public List<String> findChatLookUpHistoryByUsername(String username) {
        return lookUpHistoryRepository.findChatLookUpHistoryByUsername(username);
    }

    // method to get all the friend's LookUpHistory of a user by username
    public List<String> findUserLookUpHistoryByUsername(String username) {
        return lookUpHistoryRepository.findUserLookUpHistoryByUsername(username);
    }

    // method to delete LookUpHistory
    @Transactional
    public void deleteLookUpHistory(String username, String lookUpInfo, Integer lookUpType) {
        lookUpHistoryRepository.deleteLookUpHistoryByUsernameAndLookUpInfoAndLookUpType(username, lookUpInfo, lookUpType);
    }

    // method to check if the record is added to table
    public boolean isLookUpHistoryAdded(String username, String lookUpInfo, Integer lookUpType) {
        Integer numberOfLookUpHistoryMatched = lookUpHistoryRepository.findNumberOfLookUpHistoryByUsernameAndLookUpInfoAndLookUpType(username, lookUpInfo, lookUpType);

        if (numberOfLookUpHistoryMatched == 0) {
            return false;
        }

        return true;
    }
}
