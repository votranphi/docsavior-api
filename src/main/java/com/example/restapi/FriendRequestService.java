package com.example.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendRequestService {
    @Autowired
    private FriendRequestRepository friendRequestRepository;

    // find all requesters
    public List<String> findFriendsByUsername(String username) {
        return friendRequestRepository.findRequestersByUsername(username);
    }

    public void saveNewFriendRequest(String username, String requester) {
        FriendRequest friendRequest = new FriendRequest(username, requester);
        friendRequestRepository.save(friendRequest);
    }
}
