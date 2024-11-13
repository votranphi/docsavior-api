package com.example.docsavior_api.Friend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class FriendService {
    @Autowired
    private FriendRepository friendRepository;

    // find all username's friends
    public List<String> findFriendsByUsername(String username) {
        return friendRepository.findFriendsByUsername(username);
    }

    public void saveNewFriend(String username, String usernameFriend) {
        Friend friend = new Friend(username, usernameFriend);
        friendRepository.save(friend);
    }

    @Transactional
    public void deleteFriend(String username, String usernameFriend) {
        friendRepository.deleteFriend(username, usernameFriend);
    }
}
