package com.example.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
